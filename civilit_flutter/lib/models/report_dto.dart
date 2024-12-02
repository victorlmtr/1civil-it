import 'dart:convert';

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
  final String pictureUrl;
  final String data;

  PictureDto({this.id, required this.pictureUrl, required this.data});

  Map<String, dynamic> toJson() => {
    'id': id,
    'pictureUrl': pictureUrl,
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
  final int userId;
  final DateTime creationDate;
  final String comment;
  final ReportTypeDto reportType;
  final LocationDto location;
  final List<PictureDto> pictures;

  ReportDto({
    this.id,
    required this.userId,
    required this.creationDate,
    required this.comment,
    required this.reportType,
    required this.location,
    required this.pictures,
  });

  Map<String, dynamic> toJson() => {
    'id': id,
    'userId': userId,
    'creationDate': creationDate.toIso8601String(),
    'comment': comment,
    'reportType': reportType.toJson(),
    'location': location.toJson(),
    'pictures': pictures.map((p) => p.toJson()).toList(),
  };
}
