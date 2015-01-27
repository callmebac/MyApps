package csu.example.myknn;

public class MyKnnNode {
		private int index;
		private double dimension[];//��ֵ
		private double instance;//��������ľ���
		private String classification;
		
		public MyKnnNode(int index,double dimension[]) {
			this.index = index;
			this.dimension = dimension ;
		}
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public double[] getDimension() {
			return dimension;
		}
		public void setDimension(double[] dimension) {
			this.dimension = dimension;
		}
		public double getInstance() {
			return instance;
		}
		public void setInstance(double instance) {
			this.instance = instance;
		}
		public String getClassification() {
			return classification;
		}
		public void setClassification(String classification) {
			this.classification = classification;
		}

	}
