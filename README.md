# Custom Frame
This is an API which allowes to custom bordering JFrames.
All themes work on All Operating Systems (which provide a graphical environment)
There are different pre defined borders. This borders are called Themes.
## Installation
> You need `maven` for the installation
```bash
git clone https://github.com/nbrugger-tgm/Niton-Media-Framework/
cd Niton-Media-Framework
mvn install
```
## Predefined Themes
#### FlatDarkTheme
This theme is a custom by me designed theme which looks simple and dark.
But you can change any used color as some of the size values
#### OSXTheme
This is an recreation of the Latest MAC OSX Theme. It is very very simmilar.
The only difference i didn't managed to implement are the slightly rounded corners.
Here you dont have many customisation options as apples windows are not changeable.
You only can decide between 3 Sizes predefined by me.
#### Windows10Theme
This is a recreation of the default Windows 10 Frame borders.
There is a tiny issue when you hover very fast over the top action buttons, they might dont lose the hover animation.
This Theme is highly responsiv and also customizable. You can play with nearly all colors and sizes which makes sens to be changeable
## Custom Frames
#### Theme
This is the base of all themes.
By extending this class you may write your own themes.
>This calss is very basic and has no implementations
Means you need to do everything by hand

#### ResizeableThemee
This Class is meant to be the base for all themes.
The different to the basic Theme is that it has much more implementations like:
- resizing
- moving
- draging
- corner fitting

> If you like to wrie a custom theme we highly recommend this one.

The only reason to not use this class and use Theme instead are if:

- You notice issues in the resizing or moving
- You need to make a theme which has different functions
