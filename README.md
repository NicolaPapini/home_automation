# Home automation system
This is my university project for the Programming Methodologies course. The project aims to develop a prototype of a software system for home automation, specifically focusing on automatic lighting control. The system utilizes motion and brightness sensors to detect movement and changes in ambient light, automatically adjusting the lighting accordingly. Additionally, it features a logging service that allows users to access and retrieve sensor data.

## Project goal
The objective of the project was to develop a prototype software system using design principles and patterns covered in the course, while maintaining clean, well-readable code. Additionally, the implemented functionalities were thoroughly tested to ensure correct behavior. The following design patterns were used:
- Observer. Used for managing sensor notifications to interested systems.
- Visitor: Implemented to handle different sensor event types efficiently.
- Strategy: Utilized to provide flexibility in sorting log entries based on various criteria.
