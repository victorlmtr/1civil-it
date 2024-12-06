import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'login_screen.dart';

class CreateAccountScreen extends StatefulWidget {
  const CreateAccountScreen({super.key});

  @override
  _CreateAccountScreenState createState() =>
      _CreateAccountScreenState();
}

class _CreateAccountScreenState extends State<CreateAccountScreen> {
  final ImagePicker _picker = ImagePicker();
  File? _image;

  // Controllers for form fields
  TextEditingController firstNameController = TextEditingController();
  TextEditingController lastNameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController confirmPasswordController = TextEditingController();
  TextEditingController phoneController = TextEditingController();
  TextEditingController postcodeController = TextEditingController();

  // Variables for city and INSEE code
  String? selectedCity;
  String? selectedInseeCode;
  String? selectedPostcode;
  bool isCityListVisible = false;

  // Function to pick an image from the gallery
  Future<void> _importImage() async {
    final XFile? pickedFile = await _picker.pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() {
        _image = File(pickedFile.path);
      });
    }
  }

  // Function to take a picture with the camera
  Future<void> _takePicture() async {
    final XFile? pickedFile = await _picker.pickImage(source: ImageSource.camera);
    if (pickedFile != null) {
      setState(() {
        _image = File(pickedFile.path);
      });
    }
  }

  // Function to show bottom sheet with image options
  void _showImageOptions(BuildContext context) {
    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return SafeArea(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              ListTile(
                leading: const Icon(Icons.photo_library),
                title: const Text('Importer une photo'),
                onTap: () {
                  Navigator.pop(context);
                  _importImage();
                },
              ),
              ListTile(
                leading: const Icon(Icons.camera_alt),
                title: const Text('Prendre une photo'),
                onTap: () {
                  Navigator.pop(context);
                  _takePicture();
                },
              ),
            ],
          ),
        );
      },
    );
  }

  // Function to fetch cities based on postcode using the French Government Address API
  Future<List<String>> _fetchCities(String postcode) async {
    final response = await http.get(Uri.parse(
        'https://api-adresse.data.gouv.fr/search/?q=$postcode&limit=10&autocomplete=1'));

    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      final Set<String> uniqueCities = {};  // Use a Set to automatically handle duplicates

      for (var feature in data['features']) {
        final cityName = feature['properties']['city'];
        final inseeCode = feature['properties']['citycode'];
        if (cityName != null && cityName.isNotEmpty) {
          final fullCity = '$cityName - $postcode ($inseeCode)';
          uniqueCities.add(fullCity);  // Add to set to filter duplicates
        }
      }

      return uniqueCities.toList();  // Convert Set back to List for displaying in the UI
    } else {
      throw Exception('Failed to load cities');
    }
  }

// Function to create user via API
  Future<void> _createUser() async {
    if (passwordController.text != confirmPasswordController.text) {
      _showErrorDialog('Les mots de passe ne correspondent pas');
      return;
    }

    // Prepare the payload data
    final Map<String, dynamic> userData = {
      'firstname': firstNameController.text,
      'lastname': lastNameController.text,
      'email': emailController.text,
      'password': passwordController.text,
      'phonenumber': phoneController.text,
      'city': {
        'cityname': selectedCity,
        'postcode': selectedPostcode,
        'inseecode': selectedInseeCode,
      },
    };

    try {
      // Send POST request to create user
      final response = await http.post(
        Uri.parse('http://192.168.1.21:8081/api-user/users?sourceApp=mobile'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(userData),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        // User created successfully
        _showSuccessDialog();  // Show success dialog to inform the user
      } else {
        // Handle server errors
        _showErrorDialog('Erreur lors de la création de l\'utilisateur');
      }
    } catch (e) {
      _showErrorDialog('Erreur de connexion');
    }
  }

// Function to show a success dialog
  void _showSuccessDialog() {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Compte créé'),
          content: const Text(
            'Votre compte a été créé avec succès. Veuillez vérifier votre boîte mail pour confirmer votre compte.',
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);  // Close the dialog
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const LoginScreen(), // Redirect to Login Screen
                  ),
                );
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }


  // Function to show error dialog
  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Erreur'),
          content: Text(message),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Créer un compte'),
        backgroundColor: Theme.of(context).colorScheme.primary,
        foregroundColor: Theme.of(context).colorScheme.onPrimary,
      ),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              // Title
              Text(
                'Créer un compte',
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Theme.of(context).colorScheme.primary,
                ),
              ),
              const SizedBox(height: 16),

              // First name input
              TextField(
                controller: firstNameController,
                decoration: InputDecoration(
                  labelText: 'Prénom',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // Last name input
              TextField(
                controller: lastNameController,
                decoration: InputDecoration(
                  labelText: 'Nom',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // Email input
              TextField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: 'Email',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                keyboardType: TextInputType.emailAddress,
              ),
              const SizedBox(height: 16),

              // Password input
              TextField(
                controller: passwordController,
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'Mot de passe',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // Confirm Password input
              TextField(
                controller: confirmPasswordController,
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'Confirmer le mot de passe',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // Phone number input
              TextField(
                controller: phoneController,
                decoration: InputDecoration(
                  labelText: 'Numéro de téléphone',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                keyboardType: TextInputType.phone,
              ),
              const SizedBox(height: 16),

              // Postcode input and search
              TextField(
                controller: postcodeController,
                decoration: InputDecoration(
                  labelText: 'Code postal',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                keyboardType: TextInputType.number,
                onChanged: (postcode) {
                  setState(() {
                    isCityListVisible = postcode.isNotEmpty;
                  });
                },
              ),
              const SizedBox(height: 16),

              // City selection based on postcode search
              if (postcodeController.text.isNotEmpty && isCityListVisible)
                FutureBuilder<List<String>>(
                  future: _fetchCities(postcodeController.text),
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.waiting) {
                      return CircularProgressIndicator();
                    } else if (snapshot.hasError) {
                      return Text('Error fetching cities');
                    } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                      return Text('No cities found');
                    } else {
                      return Column(
                        children: snapshot.data!.map((city) {
                          return ListTile(
                            title: Text(city),
                            onTap: () {
                              setState(() {
                                selectedCity = city.split(' - ')[0];
                                selectedPostcode = postcodeController.text;
                                selectedInseeCode = city.split('(')[1].split(')')[0];
                                isCityListVisible = false; // Close list on city selection
                              });
                            },
                          );
                        }).toList(),
                      );
                    }
                  },
                ),
              const SizedBox(height: 16),

              // Register button
              ElevatedButton(
                onPressed: _createUser,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Theme.of(context).colorScheme.primary,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                child: Text(
                  'S\'inscrire',
                  style: TextStyle(
                    fontSize: 18,
                    color: Theme.of(context).colorScheme.onPrimary,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
