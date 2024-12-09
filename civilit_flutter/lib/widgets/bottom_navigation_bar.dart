import 'package:flutter/material.dart';
import 'package:civilit_flutter/screens/home_screen.dart';
import 'package:civilit_flutter/screens/my_reports_screen.dart';
import 'package:civilit_flutter/screens/settings_screen.dart';

class BottomNavBar extends StatelessWidget {
  final int selectedIndex;
  final ValueChanged<int> onItemTapped;

  const BottomNavBar({
    Key? key,
    required this.selectedIndex,
    required this.onItemTapped,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: selectedIndex,
      onTap: (index) {
        onItemTapped(index);

        // Navigate to corresponding screen based on selected index
        if (index == 0) {
          // Navigate to the "HomeScreen" when the first item is tapped (Signaler)
          Navigator.pushAndRemoveUntil(
            context,
            MaterialPageRoute(builder: (context) => const HomeScreen()),
                (Route<dynamic> route) => false, // Remove all previous screens
          );
        } else if (index == 1) {
          // Navigate to "MyReportsScreen" when the second item is tapped (Mes signalements)
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const MyReportsScreen()),
          );
        } else if (index == 2) {
          // Navigate to "SettingsScreen" when the third item is tapped (Paramètres)
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const SettingsScreen()),
          );
        }
      },
      items: const [
        BottomNavigationBarItem(
          icon: Icon(Icons.document_scanner),
          label: 'Signaler',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.history),
          label: 'Mes signalements',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.settings),
          label: 'Paramètres',
        ),
      ],
    );
  }
}
