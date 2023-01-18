# TvT
> Group: Cristian Turbeville & Seth Morris

### Notable design features
- The program uses kinematic equations to calculate the resultant position of a projectile. <br />
- The program's tanks are not aware of their position, as they are PositionCapture objects, which control their position along the EnvironmentPane. <br />
- The program's Controller class also contains functionality in place of random terrain, as the kinematic equations are aware of height differences and can use the correct formula appropriately.
---
### Bugs
- The algorithm for checking if a successful hit occurred will not accommodate for the appropriate position, resulting in the missle's radius of blast overlapping the enemy tank, but not registering a loss of condition (health).
- Currently, the angle and text for a tank's angle/power does not change according to the turn end, a user must move by clicking "A" or "D" for the text to display above the appropriate tank.
- QuadCurve for flight path will not display correctly.
---

## Main Success

```
KeyPress:
W = Power +1 Max is Tank's power maximum (set on tank creation)
S = Power -1 Min is 1
A = Angle of Tank Launcher -1
D = Angle of Tank Launcher +1
Space = Set calculations AND Prepare for Missle Launch
Enter = Launch Missle
R = Restart Game

Main success Missle Launch:
User clicks Key "W" to increase initical velocity applied at launch
User clicks Key "D" to increase angle at which a missle will fire
User clicks Key "Space" to check if their Tank is ready for launch
User clicks Key "Enter" to fire missle
User clicks Key "R" to restart the game
```
---

### Example
![Main Success Example](/assets/msc.png)

### UML
![UML Diagram](/assets/umldiag2.png)
_[UML File](/assets/umldiag2.png)_

#### Work Concluded

Total Hours: ~24

Cristian Turbeville:
- Calculation
- Controller
- MissleLaunch
- PositionCapture
- Interfaces
- Objects (Tank, Missle)
- Constants
- UML

Seth Morris: 
- EnvironmentPane
- Controller
- PositionCapture
- Testing
- Interfaces
- Objects (Tank, Missle)
- Presentation
- UML

### Extra Tools

[JavaFX](https://openjfx.io/)
[.jar](https://cs.sfasu.edu/dsingh/)
