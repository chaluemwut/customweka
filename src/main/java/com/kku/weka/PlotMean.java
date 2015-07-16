package com.kku.weka;

import java.util.List;

import org.jfree.ui.RefineryUtilities;

public class PlotMean {
	private List<Double> lst25;
	private List<Double> lst50;
	private List<Double> lst75;

	public PlotMean(List<Double> lst25, List<Double> lst50, List<Double> lst75) {
		this.lst25 = lst25;
		this.lst50 = lst50;
		this.lst75 = lst75;
	}

	public void plot() {
		JFreeChartPlot plot = new JFreeChartPlot("", lst25, lst50, lst75);
		plot.pack();
		RefineryUtilities.centerFrameOnScreen(plot);
		plot.setVisible(true);
	}

}
