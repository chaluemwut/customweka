package com.kku.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TableBuilder {
	List<String[]> rows = new LinkedList<String[]>();

	public void addRow(String... cols) {
		rows.add(cols);
	}

	private int[] colWidths() {
		int cols = -1;

		for (String[] row : rows)
			cols = Math.max(cols, row.length);

		int[] widths = new int[cols];

		for (String[] row : rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				widths[colNum] = Math.max(widths[colNum],
						StringUtils.length(row[colNum]));
			}
		}

		return widths;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();

		int[] colWidths = colWidths();

		for (String[] row : rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				buf.append(StringUtils.rightPad(
						StringUtils.defaultString(row[colNum]),
						colWidths[colNum]));
				buf.append(' ');
			}

			buf.append('\n');
		}

		return buf.toString();
	}

	public static void main(String[] args) {
		TableBuilder tlb = new TableBuilder();
		tlb.addRow("----", "----", "----", "----");
		tlb.addRow("Size ", "25%", "50%", "75%");
		tlb.addRow("----", "----", "----", "----");
		tlb.addRow("native-country", "0.854", "0.52", "0.142");
		tlb.addRow("marital-status", "0.52", "0.85", "0.25");
		System.out.println(tlb);
	}

}
