CPATH=.:junit.jar:itextpdf-5.3.4.jar

all: Sketch

Sketch: Angle.java Command.java DataTest.java Geometry.java MenuItemView.java Presenter.java RearViewPane.java Sketch.java ViewPane.java CheckBoxView.java Cote.java FrontViewPane.java Measure.java MouseHandler.java RadioButtonView.java SideViewPane.java TextFieldView.java
	javac -Xlint:deprecation -cp junit.jar:itextpdf-5.3.4.jar:. DataTest.java RadioButtonView.java MenuItemView.java Presenter.java Cote.java Angle.java Measure.java MouseHandler.java Geometry.java SideViewPane.java RearViewPane.java ViewPane.java Sketch.java  


