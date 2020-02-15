package name.mymiller.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ObjectUtils {

	static public Object[] allValues(Object... objects) {
		final ArrayList<Object> values = new ArrayList<>();
		for (final Object obj : objects) {
			if (obj != null) {
				if (obj instanceof List<?>) {
					values.addAll(((List<?>) obj));
				} else if (obj instanceof Map<?, ?>) {
					values.addAll(((Map<?, ?>) obj).values());
				} else if (obj.getClass().isArray() && (((Object[]) obj).length == 0)) {
					values.addAll(ObjectUtils.toArray(obj));
				} else {
					values.add(obj);
				}
			}
		}

		return values.toArray();
	}

	static public void forEach(Consumer<Object> consumer, Object... objects) {
		ObjectUtils.toArray(objects).forEach(consumer);
	}

	static public boolean isNotNull(Object obj) {
		return !ObjectUtils.isNull(obj);
	}

	static public boolean isNotNull(Object... objects) {
		for (final Object obj : objects) {
			if (!ObjectUtils.isNotNull(obj)) {
				return false;
			}
		}
		return true;
	}

	static public boolean isNotNullOrEmpty(Object... objects) {
		for (final Object obj : objects) {
			if (obj == null) {
				return false;
			} else if ((obj instanceof List<?>) && ((List<?>) obj).isEmpty()) {
				return false;
			} else if ((obj instanceof Map<?, ?>) && (((Map<?, ?>) obj).size() == 0)) {
				return false;
			} else if (obj.getClass().isArray() && (((Object[]) obj).length == 0)) {
				return false;
			}
		}
		return true;
	}

	static public boolean isNull(Object obj) {
		return obj == null;
	}

	static public boolean isNull(Object... objects) {
		for (final Object obj : objects) {
			if (!ObjectUtils.isNull(obj)) {
				return false;
			}
		}
		return true;
	}

	static public boolean isNullOrEmpty(Object... objects) {
		for (final Object obj : objects) {
			if (obj != null) {
				if ((obj instanceof List<?>) && !((List<?>) obj).isEmpty()) {
					return false;
				} else if ((obj instanceof Map<?, ?>) && (((Map<?, ?>) obj).size() > 0)) {
					return false;
				} else if (obj.getClass().isArray() && (((Object[]) obj).length > 0)) {
					return false;
				}
			}
		}
		return true;
	}

	static public List<Object> toArray(Object... objects) {
		return Arrays.asList(objects);
	}
}
