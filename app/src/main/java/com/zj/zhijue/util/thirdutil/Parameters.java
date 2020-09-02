package com.zj.zhijue.util.thirdutil;



import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class Parameters {
    private int limit = -1;
    private final Map<String, Map<Integer, Object>> paramHashValues = new LinkedHashMap();
    private int parameterCount = 0;
    private int parameterIndex = 0;

    public Map<String, Map<Integer, Object>> getParamHashValues() {
        return this.paramHashValues;
    }

    public void addParameter(String key, Object value) throws IllegalStateException {
        if (key != null) {
            key = key.toLowerCase();
            if (checkValuePrimitive(value)) {
                this.parameterCount++;
                if (this.limit <= -1 || this.parameterCount <= this.limit) {
                    Map values = (Map) this.paramHashValues.get(key);
                    if (values == null) {
                        values = new LinkedHashMap(1);
                        this.paramHashValues.put(key, values);
                    }
                    int i = this.parameterIndex;
                    this.parameterIndex = i + 1;
                    Integer valueOf = Integer.valueOf(i);
                    if (value == null) {
                        value = "";
                    }
                    values.put(valueOf, value);
                    return;
                }
                throw new IllegalStateException("parameters.maxCountFail: " + this.limit);
            }
            throw new IllegalArgumentException("Please use value which is primitive type like: String,Integer,Long and so on. But not Collection !");
        }
    }

    private boolean checkValuePrimitive(Object value) {
        return value == null || (value instanceof String) || (value instanceof Integer) || (value instanceof Long) || (value instanceof Boolean) || (value instanceof Float) || (value instanceof Double) || (value instanceof Character) || (value instanceof Byte) || (value instanceof Short);
    }

    public String[] getParameterValues(String name) {
        Map values = (Map) this.paramHashValues.get(name);
        if (values == null) {
            return null;
        }
        return (String[]) values.values().toArray(new String[values.size()]);
    }

    public String getParameter(String name) {
        if (name != null) {
            name = name.toLowerCase();
        }
        Map values = (Map) this.paramHashValues.get(name);
        if (values == null) {
            return "";
        }
        if (values.size() == 0) {
            return "";
        }
        String value = values.values().iterator().next().toString();
        if (value == null) {
            return "";
        }
        return value;
    }

    public String getParameterSpec(String name) {
        if (name != null) {
            name = name.toLowerCase();
        }
        Map values = (Map) this.paramHashValues.get(name);
        if (values == null || values.size() == 0) {
            return null;
        }
        String value = values.values().iterator().next().toString();
        if (value == null) {
            value = null;
        }
        return value;
    }

    public Set<String> getParameterNames() {
        return this.paramHashValues.keySet();
    }

    public String paramsAsString(ParamValueDerecator... dereactors) throws IOException {
        if (this.paramHashValues.isEmpty()) {
            return "";
        }
        ParamValueDerecator dereactor = null;
        if (dereactors != null && dereactors.length > 0) {
            dereactor = dereactors[0];
        }
        String[] paramArr = new String[this.parameterIndex];
        for (String name : this.paramHashValues.keySet()) {
            Map<Integer, String> values = (Map) this.paramHashValues.get(name);
            for (Integer index : values.keySet()) {
                String value = values.get(index).toString();
                if (dereactor != null) {
                    value = dereactor.doInValue(value);
                }
                paramArr[index.intValue()] = name + "=" + value;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String singleQuery : paramArr) {
            if (!singleQuery.isEmpty()) {
                sb.append(singleQuery).append("&");
            }
        }
        return sb.length() > 0 ? sb.subSequence(0, sb.length() - 1).toString() : "";
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return this.parameterCount;
    }
}
