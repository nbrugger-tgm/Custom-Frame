# Custom Frame
This is an API which allowes to custom bordering JFrames.
All themes work on All Operating Systems (which provide a graphical environment)
There are different pre defined borders. This borders are called Themes.
## Installation
> You need `maven` for the installation
```bash
git clone https://github.com/nbrugger-tgm/Custom-Frame/
cd Custom-Frame
mvn install
cd ..
rm Custom-Frame -r
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
## Usage Example
```java
	private static void testWindowsThemes() {
		Theme theme = new Windows10Theme(); //Create a windows 10 Theme (you can use any other theme)
		theme.setSize(Size.NORMAL); //Set the border/button size of the Theme
		CustomFrame frame = new CustomFrame(theme); //Create a Frame with this theme
		frame.setSize(300,300); //set default starting size (sorry you need this at the moment)
		frame.getContentPane().setBackground(Color.black); //Use this to do anything with the frame (add components)
		frame.setVisible(true);
		frame.setTitle("A Title");
	}

```

#### Rules
The Rules from JFrame also work on Custom Frames (except showDecorations)
```java
private static void testFrameRuling() {
		Theme theme = new Windows10Theme(); //creates a Windows 10 Theme
		theme.setSize(Size.NORMAL);
		CustomFrame frame = new CustomFrame(theme);
		frame.setSize(400,400); //set fixed size
		frame.getContentPane().setBackground(Color.black); //change color to see content pane
		frame.setVisible(true); //display
		frame.setResizable(false); //you still can disable resizing
		frame.setTitle("A very very very Long JFrame title which is longer than neccessary"); //long titles are ...ed
		frame.setMinimumSize(new Dimension(300, 300)); //if window is resizeable this rules work too
		frame.setMaximumSize(new Dimension(600, 600)); //-||-
	}
```

#### Example with panels
Here you see also sizing/grids wórk perfectly
```java
	private static void testSizing() {
		CustomFrame frame = new CustomFrame(new FlatDarkTheme());
		frame.setSize(300,300);
		frame.setVisible(true);
		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		frame.getContentPane().setLayout(new GridLayout());
		frame.getContentPane().add(panel);
		frame.getContentPane().setBackground(Color.RED);
		frame.repaint();
	}
```
#### OSX Theme
If you need it there is also a OSX theme  
```java
private static void testOSXTheme() {
		OSXTheme theme = new OSXTheme();
		theme.setSize(Size.NORMAL);
		CustomFrame frame = new CustomFrame(theme);
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.setTitle("A very very very Long JFrame title which is longer than neccessary"); //overflow handling works here too
}
```

#### Adding Components
```java
	private static void testContent() {
		CustomFrame frame = new CustomFrame(new FlatDarkTheme());
		frame.setSize(300,300);
		frame.setVisible(true);
		JButton	 panel = new JButton("I biz");
//		panel.setBackground(Color.GREEN);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(panel);
		frame.repaint();
	}
``` 
#### Create Your Own Theme
```java
	private static void testMovingAndResizing() {
		ResizeableTheme theme = new ResizeableTheme() {
			
			@Override
			public void paint(Graphics2D g) {  //Define what happens on paint
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, getFrame().getWidth(), getFrame().getHeight());
				g.setColor(Color.green);
				g.fillRect(getFrame().getWidth()-65, 0, 20, 20);
				g.setColor(Color.blue);
				g.fillRect(getFrame().getWidth()-45, 0, 20, 20);
				g.setColor(Color.red);
				g.fillRect(getFrame().getWidth()-25, 0, 20, 20);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(5, 20, getFrame().getWidth()-10, getFrame().getHeight()-20);
			}
			
			@Override
			public Rectangle getMinimizeArea() { //Every theme needs to say where the resize/maximize/close button is
				return new Rectangle(getFrame().getWidth()-65, 0, 20, 20);
			}
			
			@Override
			public Rectangle getMaximizeArea() { // --||--
				return new Rectangle(getFrame().getWidth()-45, 0, 20, 20);
			}      
      
			@Override
			public Rectangle getCloseArea() {
				return new Rectangle(getFrame().getWidth()-25, 0, 20, 20);
			}
			@Override
			public Rectangle getContentSize() { //Here you define the "viewport" (the area inside the frame where components are added to)
				return new Rectangle(5, 20, getFrame().getWidth()-10, getFrame().getHeight()-20);
			}
			
			
			
			@Override
			public int getResizeRadius() {//This defines the pixels you (the cursor) are "allowed" to be away from the border to still be able to resize the window
				return 5;
			}
			
			@Override
			public Rectangle getDragingArea() { // This is the top bar which can be used to drag the Window around
				return new Rectangle(0, 0, getFrame().getWidth(), 20);
			}
			
			@Override
			public boolean currentlyResizeable() { //you can make this generic
				return true;
			}
		};
		
		CustomFrame frame = new CustomFrame(theme); //Now use the theme 
		frame.maximize(); //Make it full screen
		frame.setVisible(true);
	}
```
