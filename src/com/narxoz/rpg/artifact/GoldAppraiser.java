package com.narxoz.rpg.artifact;

/**
 * Visitor 1: estimates resale gold value of each artifact.
 *
 * Weapons are worth base value + 5 × attackBonus.
 * Armors are worth base value + 3 × defenseBonus.
 * Rings are worth 2 × base value (rare materials).
 * Potions and Scrolls sell for their base value.
 */
public class GoldAppraiser implements ArtifactVisitor {

    private int totalGold = 0;

    public int getTotalGold() {
        return totalGold;
    }

    @Override
    public void visit(Weapon weapon) {
        int price = weapon.getValue() + 5 * weapon.getAttackBonus();
        totalGold += price;
        System.out.printf("  [GoldAppraiser] %-25s => %d gold  (base %d + atk bonus)\n",
                weapon.getName(), price, weapon.getValue());
    }

    @Override
    public void visit(Potion potion) {
        int price = potion.getValue();
        totalGold += price;
        System.out.printf("  [GoldAppraiser] %-25s => %d gold\n",
                potion.getName(), price);
    }

    @Override
    public void visit(Scroll scroll) {
        int price = scroll.getValue();
        totalGold += price;
        System.out.printf("  [GoldAppraiser] %-25s => %d gold  (spell: %s)\n",
                scroll.getName(), price, scroll.getSpellName());
    }

    @Override
    public void visit(Ring ring) {
        int price = 2 * ring.getValue();
        totalGold += price;
        System.out.printf("  [GoldAppraiser] %-25s => %d gold  (rare × 2)\n",
                ring.getName(), price);
    }

    @Override
    public void visit(Armor armor) {
        int price = armor.getValue() + 3 * armor.getDefenseBonus();
        totalGold += price;
        System.out.printf("  [GoldAppraiser] %-25s => %d gold  (base %d + def bonus)\n",
                armor.getName(), price, armor.getValue());
    }
}