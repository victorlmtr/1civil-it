import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorageService {
  final _storage = const FlutterSecureStorage();

  // Save token
  Future<void> saveToken(String token) async {
    await _storage.write(key: 'token', value: token);
  }

  // Save userId
  Future<void> saveUserId(String userId) async {
    await _storage.write(key: 'userId', value: userId);
  }

  // Retrieve token
  Future<String?> getToken() async {
    return await _storage.read(key: 'token');
  }

  // Retrieve userId
  Future<String?> getUserId() async {
    return await _storage.read(key: 'userId');
  }

  // Delete token and userId
  Future<void> deleteAll() async {
    await _storage.deleteAll();
  }
}
