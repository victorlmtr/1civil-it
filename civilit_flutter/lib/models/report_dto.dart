import 'dart:convert';
import 'package:intl/intl.dart';

class CityReportDto {
  final int? id;
  final String cityName;
  final String postcode;
  final String inseeCode;

  CityReportDto({
    this.id,
    required this.cityName,
    required this.postcode,
    required this.inseeCode,
  });

  Map<String, dynamic> toJson() => {
    'id': id,
    'cityName': cityName,
    'postcode': postcode,
    'inseeCode': inseeCode,
  };
}

class LocationDto {
  final int? id;
  final double latitude;
  final double longitude;
  final String address;
  final CityReportDto cityReport;

  LocationDto({
    this.id,
    required this.latitude,
    required this.longitude,
    required this.address,
    required this.cityReport,
  });

  Map<String, dynamic> toJson() => {
    'id': id,
    'latitude': latitude,
    'longitude': longitude,
    'address': address,
    'cityReport': cityReport.toJson(),
  };
}


class PictureDto {
  final int? id;
  final String pictureurl;
  final String data;

  PictureDto({this.id, required this.pictureurl, required this.data});

  Map<String, dynamic> toJson() => {
    'id': id,
    'pictureurl': pictureurl, // Updated here as well
    'data': data,
  };
}



class ReportTypeDto {
  final int? id;
  final String typeName;

  ReportTypeDto({this.id, required this.typeName});

  Map<String, dynamic> toJson() => {
    'id': id,
    'typeName': typeName,
  };
}


class ReportDto {
  final int? id;
  final int userid;
  final DateTime creationdate;
  final String comment;
  final ReportTypeDto typeid;
  final LocationDto locationid;
  final List<PictureDto> pictures;

  ReportDto({
    this.id,
    required this.userid,
    required this.creationdate,
    required this.comment,
    required this.typeid,
    required this.locationid,
    required this.pictures,
  });

  Map<String, dynamic> toJson() {
    final dateFormat = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Ensure ISO 8601 format with milliseconds
    return {
      'id': id,
      'userid': userid,
      'creationdate': dateFormat.format(creationdate.toUtc()), // Format as UTC
      'comment': comment,
      'typeid': typeid.toJson(),
      'locationid': locationid.toJson(),
      'pictures': pictures.map((p) => p.toJson()).toList(),
    };
  }
}
