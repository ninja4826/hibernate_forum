package me.ninja4826.forum.model.query;

import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Restrictions;

public class QueryExpression {
	public static enum Operator {
		EQ,
		NE,
		GT,
		LT,
		GE,
		LE,
		LIKE
	};
	
	private String propertyName;
	private Operator operator;
	private Object value;
	
	public QueryExpression(String propertyName, String operator, Object value) {
		this.propertyName = propertyName;
		this.value = value;
		switch (operator) {
			case "=": this.operator = Operator.EQ; break;
			case "!": this.operator = Operator.NE; break;
			case ">": this.operator = Operator.GT; break;
			case "<": this.operator = Operator.LT; break;
			case ">=": this.operator = Operator.GE; break;
			case "<=": this.operator = Operator.LE; break;
			case "~": this.operator = Operator.LIKE; break;
		}
	}
	
	public QueryExpression(String propertyName, Operator operator, String value) {
		this.propertyName = propertyName;
		this.value = value;
		
	}
	
	public SimpleExpression run() {
		SimpleExpression se = null;
		switch(this.operator) {
		case EQ: se = Restrictions.eq(this.propertyName, this.value); break;
		case NE: se = Restrictions.ne(this.propertyName, this.value); break;
		case GT: se = Restrictions.gt(this.propertyName, this.value); break;
		case LT: se = Restrictions.lt(this.propertyName, this.value); break;
		case GE: se = Restrictions.ge(this.propertyName, this.value); break;
		case LE: se = Restrictions.le(this.propertyName, this.value); break;
		case LIKE: se = Restrictions.like(this.propertyName, this.value); break;
		}
		return se;
	}
}
