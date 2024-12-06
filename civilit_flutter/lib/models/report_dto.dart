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

  // fromJson method to map JSON to CityReportDto
  factory CityReportDto.fromJson(Map<String, dynamic> json) {
    return CityReportDto(
      id: json['id'],
      cityName: json['cityName'],
      postcode: json['postcode'],
      inseeCode: json['inseeCode'],
    );
  }

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
  final String? address;
  final CityReportDto cityReport;

  LocationDto({
    this.id,
    required this.latitude,
    required this.longitude,
    this.address,
    required this.cityReport,
  });

  // fromJson method to map JSON to LocationDto
  factory LocationDto.fromJson(Map<String, dynamic> json) {
    return LocationDto(
      id: json['id'],
      latitude: json['latitude'],
      longitude: json['longitude'],
      address: json['address'],
      cityReport: CityReportDto.fromJson(json['cityReport']),
    );
  }

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
  final String? data; // Nullable field

  PictureDto({this.id, required this.pictureurl, this.data});

  // Updated fromJson constructor to handle null data field
  factory PictureDto.fromJson(Map<String, dynamic> json) {
    return PictureDto(
      id: json['id'],
      pictureurl: json['pictureurl'] ?? '', // Handle null pictureurl
      data: json['data'] ?? '', // Provide default empty string if null
    );
  }

  Map<String, dynamic> toJson() =>
      {
        'id': id,
        'pictureurl': pictureurl,
        'data': data ?? '', // Ensure data is never null
      };
}

class ReportTypeDto {
  final int? id;
  final String typename;

  ReportTypeDto({this.id, required this.typename});

  // fromJson method to map JSON to ReportTypeDto
  factory ReportTypeDto.fromJson(Map<String, dynamic> json) {
    return ReportTypeDto(
      id: json['id'],
      typename: json['typename'],
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'typeName': typename,
  };
}

class ReportDto {
  final int? id;
  final int userid;
  final DateTime creationdate;
  final String? comment;
  final ReportTypeDto typeid;
  final LocationDto locationid;
  final List<PictureDto> pictures;

  ReportDto({
    this.id,
    required this.userid,
    required this.creationdate,
    this.comment,
    required this.typeid,
    required this.locationid,
    required this.pictures,
  });

  // Updated fromJson constructor to handle null comment and other nullable fields
  factory ReportDto.fromJson(Map<String, dynamic> json) {
    return ReportDto(
      id: json['id'],
      userid: json['userid'],
      creationdate: DateTime.parse(json['creationdate']),  // Parse ISO 8601 date string
      comment: json['comment'] ?? '', // Provide empty string if null
      typeid: ReportTypeDto.fromJson(json['typeid']),
      locationid: LocationDto.fromJson(json['locationid']),
      pictures: (json['pictures'] as List)
          .map((e) => PictureDto.fromJson(e))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() {
    final dateFormat = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Ensure ISO 8601 format with milliseconds
    return {
      'id': id,
      'userid': userid,
      'creationdate': dateFormat.format(creationdate.toUtc()), // Format as UTC
      'comment': comment ?? '',  // Provide default empty string if null
      'typeid': typeid.toJson(),
      'locationid': locationid.toJson(),
      'pictures': pictures.map((p) => p.toJson()).toList(),
    };
  }
}
