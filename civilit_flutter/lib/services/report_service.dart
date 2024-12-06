import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:geolocator/geolocator.dart';
import 'package:http/http.dart' as http;
import '../models/report_dto.dart';

Future<void> createReport(BuildContext context) async {
  final image = await _captureImage();
  if (image == null) {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('No image captured.')),
    );
    return;
  }

  final location = await _getCurrentLocation(context);
  if (location == null) {
    return;
  }

  final addressData = await _fetchAddress(location.latitude, location.longitude);
  if (addressData == null) {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Unable to fetch address details.')),
    );
    return;
  }

  final int reportId = 123; // Replace with logic to fetch max ID + 1
  final pictureUrl = await _uploadImage(image, reportId);
  if (pictureUrl == null) {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Failed to upload image.')),
    );
    return;
  }

  // Construct city report
  final cityReport = CityReportDto(
    id: reportId,
    cityName: addressData['city'],
    postcode: addressData['postcode'],
    inseeCode: addressData['id'],
  );

  // Construct location object
  final reportLocation = LocationDto(
    id: reportId,
    latitude: location.latitude,
    longitude: location.longitude,
    address: addressData['label'],
    cityReport: cityReport,
  );

  // Construct picture object
  final picture = PictureDto(
    id: reportId,
    pictureurl: pictureUrl,
    data: "test",
  );

  // Construct report type object
  final reportType = ReportTypeDto(
    id: 1,
    typeName: "Stationnement interdit",
  );

  // Create the full report object
  final report = ReportDto(
    id: reportId,
    userid: 1, // Use dynamic user ID if available
    creationdate: DateTime.now(),
    comment: "test",
    typeid: reportType,
    locationid: reportLocation,
    pictures: [picture],
  );

  // Send the report to the backend
  await _sendReportToBackend(report, context);
}

Future<void> _sendReportToBackend(ReportDto report, BuildContext context) async {
  const apiUrl = 'http://192.168.1.21:8082/api/reports/reports'; // Backend API endpoint

  try {
    final response = await http.post(
      Uri.parse(apiUrl),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(report.toJson()),
    );
    print('Sending report with: ${json.encode(report.toJson())}');

    if (response.statusCode == 201) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Report successfully created!')),
      );
    } else {
      print('Failed to create report: ${response.statusCode}');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Failed to create report.')),
      );
    }
  } catch (e) {
    print('Error sending report: $e');
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Error sending report to backend.')),
    );
  }
}

Future<File?> _captureImage() async {
  final picker = ImagePicker();
  final XFile? photo = await picker.pickImage(source: ImageSource.camera);
  return photo != null ? File(photo.path) : null;
}

Future<Position?> _getCurrentLocation(BuildContext context) async {
  if (!await Geolocator.isLocationServiceEnabled()) {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Location services are disabled.')),
    );
    return null;
  }

  LocationPermission permission = await Geolocator.checkPermission();
  if (permission == LocationPermission.denied) {
    permission = await Geolocator.requestPermission();
    if (permission == LocationPermission.denied) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Location permissions are denied.')),
      );
      return null;
    }
  }

  return await Geolocator.getCurrentPosition();
}

Future<Map<String, dynamic>?> _fetchAddress(double latitude, double longitude) async {
  final apiUrl = 'https://api-adresse.data.gouv.fr/reverse/?lon=$longitude&lat=$latitude';
  try {
    final response = await http.get(Uri.parse(apiUrl));
    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      return data['features']?.isNotEmpty == true ? data['features'][0]['properties'] : null;
    }
  } catch (e) {
    print('Error fetching address: $e');
  }
  return null;
}

Future<String?> _uploadImage(File image, int reportId) async {
  final uploadUrl = 'https://images.victorl.xyz/upload';

  try {
    final request = http.MultipartRequest('POST', Uri.parse(uploadUrl))
      ..files.add(await http.MultipartFile.fromPath('files', image.path));

    final response = await request.send();

    final responseBody = await response.stream.bytesToString();
    print('Response Body: $responseBody'); // Debugging

    if (response.statusCode == 200) {
      final responseJson = json.decode(responseBody);
      final fileUrls = responseJson['urls'] as List;
      return fileUrls.isNotEmpty ? fileUrls[0] : null;
    } else {
      print('Failed to upload image: ${response.statusCode}');
    }
  } catch (e) {
    print('Error uploading image: $e');
  }

  return null;
}
