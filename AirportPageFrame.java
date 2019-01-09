import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AirportPageFrame extends JFrame {
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel container;
	private String airportNameText;
	private String airportCodeNameText;
	private String airportTownText;
	private String airportCountryText;
	private String airport2Text;
	private String directText;
	private String indText;
	private String fileText;
	private JTextField airportName;
	private JTextField airportCodeName;
	private JTextField airportTown;
	private JTextField airportCountry;
	private JTextField airport2;
	private JTextArea indResults;
	private JTextArea directResults;
	private JButton button2;
	private JButton button3;
	private JList<String> nameList;
	private boolean hasAirport = false;
	private Airport a1 = null;
	private AirportPageFrame af;

	

	public AirportPageFrame() {
		
		

		Vector model= new Vector();
		
		
		nameList = new JList(model);
		
		Set<String> set = new HashSet<String>();
		
		
		int index = 0;
		
		int z = 0;
		for (int i = 0; i < CentralRegistry.airports.size(); i++) {
			if (AirportFrame.getTextField().equals(CentralRegistry.airports.get(i).getTown())) {
				airportNameText = CentralRegistry.airports.get(i).getName();
				airportCodeNameText = CentralRegistry.airports.get(i).getCodeName();
				airportTownText = CentralRegistry.airports.get(i).getTown();
				airportCountryText = CentralRegistry.airports.get(i).getCountry();
				index = i;
				a1 = CentralRegistry.airports.get(i);
				hasAirport = true;
			}
		}

		if (hasAirport == true) {
			af=this;
			for (int j = 0; j < CentralRegistry.flights.size(); j++) {
				if (CentralRegistry.airports.get(index).equals(CentralRegistry.flights.get(j).getAirportA())) {
					if (CentralRegistry.flights.get(j).getAirportA()
							.isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportB())) {
						model.add(z, CentralRegistry.flights.get(j).getCompany());
						z++;
					}
				}
			}
			
			set.addAll(model);
			model.clear();
			model.addAll(set);
			
			Collections.sort(model);
			
			
			container= new JPanel();
			panel2 = new JPanel();
			panel3=new JPanel();
			panel4=new JPanel();
			panel5=new JPanel();
			
			button2 = new JButton("Find Flights");
			button3= new JButton("Back to Search Screen");
			airportName = new JTextField(airportNameText, 10);
			airportCodeName = new JTextField(airportCodeNameText, 10);
			airportTown = new JTextField(airportTownText, 10);
			airportCountry = new JTextField(airportCountryText, 10);
			airport2Text = "Insert town";
			airport2 = new JTextField(airport2Text, 10);
			directResults = new JTextArea(directText);
			directResults.setBorder((BorderFactory.createLineBorder(Color.gray)));
			directResults.setPreferredSize(new Dimension(350, 170));
			indResults=new JTextArea(indText);
			indResults.setBorder(BorderFactory.createLineBorder(Color.gray));
			indResults.setPreferredSize(new Dimension(350,170));

			ButtonListener listener2 = new ButtonListener();
			button2.addActionListener(listener2);
			button3.addActionListener(listener2);
			airport2.addActionListener(listener2);
				
			panel2.add(airportName);
			panel2.add(airportCodeName);
			panel2.add(airportTown);
			panel2.add(airportCountry);
			panel2.add(nameList);
			panel4.add(airport2);
			panel4.add(button2);
			panel3.add(directResults);
			panel3.add(indResults);
			panel5.add(button3);
			panel2.setBorder(BorderFactory.createLineBorder(Color.gray));
			

			this.setVisible(true);
			this.setSize(750, 500);
			this.setTitle("Airport page");
			this.setLocation(550, 250);
			container.add(panel2);
			container.add(panel4);
			container.add(panel3);
			container.add(panel5);
			
			this.setContentPane(container);
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		
		} else if (hasAirport == false) {
			JOptionPane.showMessageDialog(this, AirportFrame.getTextField() + " " + "does not have an airport"); 
		}
		
	}

	class ButtonListener implements ActionListener {
		int index=0;
		boolean check=true;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(button2)) {
				directResults.setText(null);
				indResults.setText(null);
				airport2Text=airport2.getText();
				for (int i = 0; i < CentralRegistry.airports.size(); i++) {
					if (airport2Text.equals(CentralRegistry.airports.get(i).getTown())) {
						if(CentralRegistry.airports.get(i).equals(a1)) {
							JOptionPane.showMessageDialog(null,"Arrival and Departure city cannot be the same");
						}
						else if (a1.isDirectlyConnectedTo(CentralRegistry.airports.get(i))) {
							directResults.setText(CentralRegistry.getDirectFlightsDetails(CentralRegistry.airports.get(i), a1));
							index=i;
							
						}
						else if(a1.isInDirectlyConnectedTo(CentralRegistry.airports.get(i))) {
							indResults.setText(CentralRegistry.getInDirectFlightsDetails(a1, CentralRegistry.airports.get(i)));
							index=i;
							check=false;
						}
					}
				}
				fileText=a1.getTown()+"to"+airport2Text+".txt";
				File file=new File(fileText);
				try {
					FileWriter writer = new FileWriter(file); 
					writer.write("CITY: "+a1.getTown()+", "+a1.getCountry());
					writer.write(System.lineSeparator());
					writer.write("Airport: "+a1.getName()+"("+a1.getCodeName()+")");
					writer.write(System.lineSeparator());
					writer.write(System.lineSeparator());
					writer.write("DESTINATION: "+airport2Text);
					writer.write(System.lineSeparator());
					writer.write(System.lineSeparator());
					if(check) {
						writer.write(CentralRegistry.getDirectFlightsDetails(CentralRegistry.airports.get(index), a1));
					}
					else {
						writer.write(CentralRegistry.getInDirectFlightsDetails(a1, CentralRegistry.airports.get(index)));
					}
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(arg0.getSource().equals(button3)) {
				af.dispose();
				AirportFrame af1=new AirportFrame();
			}
			CentralRegistry.flightsConnected.clear();
			CentralRegistry.airportsIndConnected.clear();
		}
		
	}
}
