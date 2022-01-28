package shop;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Basket {

    public static final int FIRST_ITEM = 1;
    private final Map<Item, Integer> cached = new HashMap<>();

    public void addItem(Item item) {
        addItems(item, 1);
    }

    public void removeItem(Item item) {
        removeItems(item, 1);
    }

    public void addItems(Item item, Integer amount) {
        Integer cachedAmount = cached.getOrDefault(item, 0);
        if (amount < 1) {
            throw new NullPointerException();
        }
        cached.put(item, cachedAmount + amount);

    }

    public void removeItems(Item item, int amount) {
        Integer cachedAmount = cached.get(item);
        if (amount > cachedAmount) {
            throw new NullPointerException();
        } else if (amount == cachedAmount) {
            cached.remove(item);
        } else {
            cached.put(item, cachedAmount - amount);
        }
    }

    public BigDecimal calculateOrder() {
        if (cached.isEmpty()) {
            throw new NullPointerException();
        }
        Optional<BigDecimal> sum = cached.entrySet()
                .stream()
                .map(mapEl ->
                        BigDecimal.valueOf(mapEl.getKey().getPrice())
                                .multiply(BigDecimal.valueOf(mapEl.getValue())))
                .reduce(BigDecimal::add);
        return sum.orElse(BigDecimal.valueOf(0.0));
    }

    public String showOrder() {
        if (cached.isEmpty()) {
            throw new NullPointerException();
        }
        return cached.entrySet()
                .stream()
                .map(mapEl ->
                        mapEl.getKey().getName()
                                + " " + mapEl.getValue()
                                + "szt.")
                .collect(Collectors.joining("/n"));
    }

    public boolean haveItem(Item item) {
        return cached.containsKey(item);
    }

    public int countItem(Item item) {
        return cached.getOrDefault(item, 0);
    }

    public Map<Item, Integer> getOrder() {
        return Collections.unmodifiableMap(cached);
    }
}
