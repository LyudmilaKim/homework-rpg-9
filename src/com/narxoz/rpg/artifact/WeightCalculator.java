package com.narxoz.rpg.artifact;

/**
 * Visitor 4 — Open/Closed proof.
 *
 * Calculates the carry-weight of each artifact in the inventory.
 * Added WITHOUT modifying any file in the artifact/ package hierarchy.
 *
 * Weapons and Armor contribute full weight.
 * Potions and Rings are half-weight (packed tightly).
 * Scrolls add minimal weight.
 */
public class WeightCalculator implements ArtifactVisitor {

    private int totalWeight = 0;

    public int getTotalWeight() {
        return totalWeight;
    }

    @Override
    public void visit(Weapon weapon) {
        totalWeight += weapon.getWeight();
        System.out.printf("  [WeightCalculator] %-25s %d kg  (full weapon weight)\n",
                weapon.getName(), weapon.getWeight());
    }

    @Override
    public void visit(Potion potion) {
        int w = potion.getWeight() / 2 + 1;
        totalWeight += w;
        System.out.printf("  [WeightCalculator] %-25s %d kg  (half — flask packed)\n",
                potion.getName(), w);
    }

    @Override
    public void visit(Scroll scroll) {
        int w = Math.max(1, potion(scroll));
        totalWeight += w;
        System.out.printf("  [WeightCalculator] %-25s %d kg  (parchment, light)\n",
                scroll.getName(), w);
    }

    @Override
    public void visit(Ring ring) {
        int w = Math.max(1, ring.getWeight() / 2);
        totalWeight += w;
        System.out.printf("  [WeightCalculator] %-25s %d kg  (compact ring)\n",
                ring.getName(), w);
    }

    @Override
    public void visit(Armor armor) {
        totalWeight += armor.getWeight();
        System.out.printf("  [WeightCalculator] %-25s %d kg  (full armor weight)\n",
                armor.getName(), armor.getWeight());
    }

    // Helper — avoids naming collision in visit(Scroll)
    private int potion(Scroll scroll) {
        return scroll.getWeight() / 3;
    }
}