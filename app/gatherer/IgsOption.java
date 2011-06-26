package gatherer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum IgsOption {

	CLIENT("client"), QUIET("quiet"), NMATCH("nmatch"), SEEK("seek"), NEWUNDO("newundo");

	private static final Map<String, IgsOption> lookup = new HashMap<String, IgsOption>();

	static {
		for (IgsOption e : EnumSet.allOf(IgsOption.class))
			lookup.put(e.option, e);
	}

	public static IgsOption get(String option) {
		return lookup.get(option);
	}

	public String option;

	IgsOption(String o) {
		option = o;
	}

	@Override
	public String toString() {
		return option;
	}
}
