import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class AirportFrame extends JFrame {

	public static AirportFrame af;
	private JPanel panel;
	private JButton button;
	private JButton button1;
	private static JTextField textField;
	private String diameterText;
	private JTextField diameter;

	public AirportFrame() {

		af = this;
		panel = new JPanel();
		button = new JButton("Find");
		button1= new JButton("Visualize Network");
		textField = new JTextField("Please enter City name", 15);

		panel.add(textField);
		panel.add(button);
		panel.add(button1);

		this.setVisible(true);
		this.setContentPane(panel);
		this.setSize(320, 150);
		this.setTitle("Find Airport");
		this.setLocation(550, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(panel);
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
		button1.addActionListener(listener);
		
	}

	public static String getTextField() {
		return textField.getText();
	}
	
	class ButtonListener implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(button)) {
				for (int i = 0; i < CentralRegistry.airports.size(); i++) {
					if (AirportFrame.getTextField().equals(CentralRegistry.airports.get(i).getTown())) {
						af.dispose();
					}
				}	
				
				AirportPageFrame ap = new AirportPageFrame();
			}
			else if(arg0.getSource().equals(button1)) {
				UndirectedSparseGraph graph = new UndirectedSparseGraph();		

				for(int i=0;i<CentralRegistry.flights.size();i++) {	 
					graph.addVertex(CentralRegistry.flights.get(i).getAirportA().getTown());
					graph.addVertex(CentralRegistry.flights.get(i).getAirportB().getTown());
					graph.addEdge("edge"+i,CentralRegistry.flights.get(i).getAirportA().getTown(),CentralRegistry.flights.get(i).getAirportB().getTown());
				}
				
				VisualizationImageServer vs =new VisualizationImageServer(new CircleLayout(graph), new Dimension(503, 400));
				vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
				        JFrame frame = new JFrame();
						panel=new JPanel();

						diameterText="Diameter : " + DistanceStatistics.diameter(graph);
						diameter= new JTextField(diameterText,44);
						
					    panel.add(vs);
					    panel.add(diameter);
					
					    frame.setContentPane(panel);
					    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					    frame.pack();
					    frame.setSize(500,477);
					    frame.setLocation(500,300);
					    frame.setTitle("City Airport Connections Network");
					    frame.setVisible(true);
					    frame.add(diameter);
					    //System.out.println("The graph g = " + graph.toString());
					    af.dispose();
			}
			
		}

	}
	
}
