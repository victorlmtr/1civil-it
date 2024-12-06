import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorageService {
  final _storage = const FlutterSecureStorage();

  // Save a token to secure storage
  Future<void> saveToken(String token) async {
    await _storage.write(key: 'jwt_token', value: token);
  }

  // Retrieve the token from secure storage
  Future<String?> getToken() async {
    return await _storage.read(key: 'jwt_token');
  }

  // Delete the token from secure storage
  Future<void> deleteToken() async {
    await _storage.delete(key: 'jwt_token');
  }
}
