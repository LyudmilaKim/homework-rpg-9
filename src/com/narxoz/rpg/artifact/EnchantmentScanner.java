package com.narxoz.rpg.artifact;

/**
 * Visitor 2: scans each artifact for magical properties.
 *
 * Weapons with attackBonus ≥ 10 are considered magically enhanced.
 * Rings always carry a magic signature.
 * Scrolls reveal their spell name.
 * Armors and Potions report passive aura or alchemical enchantment.
 */
public class EnchantmentScanner implements ArtifactVisitor {

    private int enchantedCount = 0;

    public int getEnchantedCount() {
        return enchantedCount;
    }

    @Override
    public void visit(Weapon weapon) {
        if (weapon.getAttackBonus() >= 10) {
            enchantedCount++;
            System.out.printf("  [EnchantmentScanner] %-25s ✦ MAGICAL  +%d attack\n",
                    weapon.getName(), weapon.getAttackBonus());
        } else {
            System.out.printf("  [EnchantmentScanner] %-25s   mundane (+%d attack)\n",
                    weapon.getName(), weapon.getAttackBonus());
        }
    }

    @Override
    public void visit(Potion potion) {
        enchantedCount++;
        System.out.printf("  [EnchantmentScanner] %-25s ✦ Alchemical aura  (heals %d)\n",
                potion.getName(), potion.getHealing());
    }

    @Override
    public void visit(Scroll scroll) {
        enchantedCount++;
        System.out.printf("  [EnchantmentScanner] %-25s ✦ Arcane script: \"%s\"\n",
                scroll.getName(), scroll.getSpellName());
    }

    @Override
    public void visit(Ring ring) {
        enchantedCount++;
        System.out.printf("  [EnchantmentScanner] %-25s ✦ Magic signature  +%d mana\n",
                ring.getName(), ring.getMagicBonus());
    }

    @Override
    public void visit(Armor armor) {
        if (armor.getDefenseBonus() >= 8) {
            enchantedCount++;
            System.out.printf("  [EnchantmentScanner] %-25s ✦ Ward aura  +%d defense\n",
                    armor.getName(), armor.getDefenseBonus());
        } else {
            System.out.printf("  [EnchantmentScanner] %-25s   plain armor (+%d defense)\n",
                    armor.getName(), armor.getDefenseBonus());
        }
    }
}