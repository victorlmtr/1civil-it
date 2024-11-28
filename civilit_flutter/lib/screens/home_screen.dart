import 'package:flutter/material.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Row(
          children: [
            Image.asset(
              'assets/images/logo.png',
              height: 50,
            ),
            const SizedBox(width: 40),
            Text(
              'Signaler',
              style: TextStyle(
                color: Theme
                    .of(context)
                    .colorScheme
                    .onSecondary,
                fontSize: 32,
              ),
            ),
          ],
        ),
        backgroundColor: Theme
            .of(context)
            .colorScheme
            .primary,
      ),
      body: Center(
        child: Material(
          elevation: 8, // Add shadow
          shape: const CircleBorder(),
          child: InkWell(
            onTap: () {
              print('Call to Action button pressed');
            },
            customBorder: const CircleBorder(),
            child: Container(
              width: 300,
              height: 300,
              decoration: BoxDecoration(
                color: Theme
                    .of(context)
                    .colorScheme
                    .tertiary,
                shape: BoxShape.circle,
              ),
              child: Center(
                child: Text(
                  'Créer un signalement',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    color: Theme
                        .of(context)
                        .colorScheme
                        .onTertiary,
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
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
        selectedItemColor: Theme
            .of(context)
            .colorScheme
            .secondary,
        unselectedItemColor: Theme
            .of(context)
            .colorScheme
            .onSurfaceVariant,
      ),
    );
  }
}
