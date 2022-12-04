# TvT
> Group: Cristian Turbeville & Seth Morris

### Notable design features: The program uses kinematic equations to calculate the resultant position of a projectile.
---
### Bugs: The Angle and Power text doesn’t switch over to the other tank when a player’s turn ends, you have to move the tank or adjust the angle or power for it to switch. The missile doesn’t connect sometimes on shots that should hit, haven’t figured out why.
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

Main success Missle Launch:
User clicks Key "W" to increase initical velocity applied at launch
User clicks Key "D" to increase angle at which a missle will fire
User clicks Key "Space" to check if their Tank is ready for launch
User clicks Key "Enter" to fire missle
```
---

## Java ReadMe Content

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
