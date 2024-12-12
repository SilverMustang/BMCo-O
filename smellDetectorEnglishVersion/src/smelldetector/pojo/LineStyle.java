package smelldetector.pojo;

public class LineStyle {
	
private Normal normal;
	
	private String color;
	
	private Double curveness;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getCurveness() {
		return curveness;
	}

	public void setCurveness(Double curveness) {
		this.curveness = curveness;
	}

	public Normal getNormal() {
		return normal;
	}

	public void setNormal(Normal normal) {
		this.normal = normal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((curveness == null) ? 0 : curveness.hashCode());
		result = prime * result + ((normal == null) ? 0 : normal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineStyle other = (LineStyle) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (curveness == null) {
			if (other.curveness != null)
				return false;
		} else if (!curveness.equals(other.curveness))
			return false;
		if (normal == null) {
			if (other.normal != null)
				return false;
		} else if (!normal.equals(other.normal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LineStyle [normal=" + normal + ", color=" + color + ", curveness=" + curveness + "]";
	}

}
