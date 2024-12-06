import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:civilit_flutter/api/SecureStorageService.dart';
import 'package:civilit_flutter/models/report_dto.dart';
import 'package:civilit_flutter/widgets/bottom_navigation_bar.dart';

final SecureStorageService _storageService = SecureStorageService();

class MyReportsScreen extends StatefulWidget {
  const MyReportsScreen({super.key});

  @override
  _MyReportsScreenState createState() => _MyReportsScreenState();
}

class _MyReportsScreenState extends State<MyReportsScreen> {
  int _selectedIndex = 1; // Default selected index for My Reports
  List<ReportDto> _reports = [];
  bool _isLoading = true;
  String _errorMessage = '';

  @override
  void initState() {
    super.initState();
    _fetchReports();
  }

  Future<void> _fetchReports() async {
    try {
      final userId = await _storageService.getUserId();
      if (userId == null) {
        setState(() {
          _errorMessage = 'User not logged in.';
          _isLoading = false;
        });
        return;
      }

      final response = await http.get(
        Uri.parse('http://192.168.1.21:8082/api/reports/reports/user/$userId'),
        headers: {'Content-Type': 'application/json'},
      );

      // Log the API response for debugging
      print('API Response Status Code: ${response.statusCode}');
      print('API Response Body: ${response.body}');

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        setState(() {
          _reports = data.map((e) => ReportDto.fromJson(e)).toList();
          _isLoading = false;
        });
      } else {
        setState(() {
          _errorMessage = 'Failed to fetch reports.';
          _isLoading = false;
        });
      }
    } catch (e) {
      setState(() {
        _errorMessage = 'Error fetching reports: $e';
        _isLoading = false;
      });
    }
  }



  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
    // The navigation logic is handled in BottomNavBar
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Mes Signalements'),
        backgroundColor: Theme.of(context).colorScheme.primary,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _errorMessage.isNotEmpty
          ? Center(child: Text(_errorMessage))
          : _reports.isEmpty
          ? const Center(
        child: Text(
          'Aucun signalement pour le moment.',
          style: TextStyle(fontSize: 18),
        ),
      )
          : ListView.builder(
        itemCount: _reports.length,
        itemBuilder: (context, index) {
          final report = _reports[index];
          return Card(
            margin: const EdgeInsets.all(8.0),
            child: ListTile(
              title: Text(report.typeid?.typename ?? 'No Type'),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Créé le: ${report.creationdate?.toLocal().toString().substring(0, 19)}',
                    style: const TextStyle(fontSize: 14),
                  ),
                  Text(
                    'Adresse: ${report.locationid?.address ?? 'No address'}',
                    style: const TextStyle(fontSize: 14),
                  ),
                ],
              ),
            ),
          );
        },
      ),
      bottomNavigationBar: BottomNavBar(
        selectedIndex: _selectedIndex,
        onItemTapped: _onItemTapped,
      ),
    );
  }
}
