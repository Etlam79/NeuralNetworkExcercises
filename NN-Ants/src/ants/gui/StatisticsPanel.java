package ants.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JComponent;

import neuralnet.gene.GenomePopulation.EpochParams;
import neuralnet.NNProperties;
import ants.AntPopulationContoller;

public class StatisticsPanel extends JComponent implements Observer {

	int width = 700;
	int height = 500;
	int widthOfParams = 120;

	private NNProperties props;
	private List<EpochParams> epochHistory;

	public StatisticsPanel(NNProperties props) {		
		this.props = props;
	
		setDoubleBuffered(true);
	
	  //setBounds(0, 600, width, 700);
		setSize(width, height);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		if (epochHistory == null) 
			return;
		
		drawParams(g, epochHistory);

		if (!epochHistory.isEmpty())
			drawChart(g, epochHistory);
	}

	private void drawParams(Graphics g, List<EpochParams> s) {
		Set<Entry<Object, Object>> fields = props.entrySet();
		int i = 0;
		for (Entry<Object, Object> field : fields) {
			try {
				String line = field.getKey() + ":" + field.getValue();
				g.drawString(line, 10, 10 + (i++ * 15));

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void drawLines(Graphics g, double height, double maxFitness) {
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < maxFitness; i += 5) {
			g.drawLine(0, (int) (i * height), width-widthOfParams, (int) (i * height));

			g.drawString(String.format("%.2f",maxFitness-i), width-widthOfParams-25,(int) (i * height)+20);
		}

	}

	private void drawChart(Graphics g, List<EpochParams> s) {
		double maxFitness = calculateMaxFitness(s, EpochParams.BEST, EpochParams.AVERAGE);
		double heightOfValue = (height-10) / maxFitness;

		g.translate(widthOfParams, 0);

		drawLines(g, heightOfValue, maxFitness);

		g.setColor(Color.black);

		g.drawString(String.format("%s) best: %s, average: %s, total: %s",
				s.size(), s.get(s.size() - 1).get(EpochParams.BEST),
				s.get(s.size() - 1).get(EpochParams.AVERAGE),
				s.get(s.size() - 1).get(EpochParams.TOTAL)), 0, height - 20);

		drawChartLines(g, s, heightOfValue);

	}

	private void drawChartLines(Graphics g, List<EpochParams> s, double heightOfValue) {
		double maxTotalFitness = calculateMaxFitness(s, EpochParams.TOTAL);
		double heightOfMaxValue = height / maxTotalFitness;
		double maxNrOfValues = 100;

		int widthOfValue = (int) ((width-widthOfParams)/ ((s.size() < maxNrOfValues) ? s.size()
				: maxNrOfValues));
		double increment = 1;
		if (s.size() > maxNrOfValues)
			increment = s.size() / maxNrOfValues;

		int previousBest = height;
		int  previousAverage = height;
		int previousTotal = height;
		

		int i = 0;
		for (double j = 0; j < s.size(); j += increment, i++) {
			EpochParams e = s.get((int) j);

			int fromX = widthOfValue * i;
			int toX = widthOfValue * i + widthOfValue;
		
			g.setColor(Color.red);
			previousBest = drawChartLine(g, fromX, toX,
					e.get(EpochParams.BEST), previousBest, heightOfValue);

			g.setColor(Color.blue);
			previousAverage = drawChartLine(g, fromX, toX,
					e.get(EpochParams.AVERAGE), previousAverage, heightOfValue);

			g.setColor(Color.green);
			previousTotal = drawChartLine(g, fromX, toX,
					e.get(EpochParams.TOTAL), previousTotal, heightOfMaxValue);
		}

	}

	private int drawChartLine(Graphics g, int fromX, int toX, double value,
			int previousBest, double heightOfValue) {
		double valueInPX = value * heightOfValue;
		int toY = (int) (height - valueInPX);

		g.drawLine(fromX, previousBest, toX, toY);
		//g.drawString(String.format("%.2f",value), toX, toY);
		return toY;
	}

	private double calculateMaxFitness(List<EpochParams> s, int... values) {
		double maxFittness = 0;
		for (EpochParams e : s) {
			for (int i = 0; i < values.length; i++) {
				if (e.get(values[i]) > maxFittness)
					maxFittness = e.get(values[i]);
			}

		}
		return maxFittness;
	}

	@Override
	public void update(Observable o, Object observerable) {
		epochHistory =	((AntPopulationContoller) o).getStats();
		
		
		int heightP = (int) getParent().getSize().getHeight();
//		 width = (int)getParent().getWidth()-1000-widthOfParams;
	width = getWidth();
		 
		 
		 //this.x = (int) getParent().getSize().getWidth();
		//setBounds(0, heightP - height, width, height);
		//setSize(width, height);
		repaint();
	}

}
