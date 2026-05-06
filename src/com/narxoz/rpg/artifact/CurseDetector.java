package com.narxoz.rpg.artifact;

/**
 * Visitor 3: detects cursed or dangerous artifacts.
 *
 * Items with value below a threshold but high combat bonus are suspicious.
 * Rings with magicBonus ≥ 15 may carry a binding curse.
 * Scrolls of unknown spells are flagged as potentially hazardous.
 */
public class CurseDetector implements ArtifactVisitor {

    private static final int CHEAP_THRESHOLD = 30;

    private int cursedCount = 0;

    public int getCursedCount() {
        return cursedCount;
    }

    @Override
    public void visit(Weapon weapon) {
        boolean cursed = weapon.getValue() < CHEAP_THRESHOLD && weapon.getAttackBonus() > 8;
        if (cursed) {
            cursedCount++;
            System.out.printf("  [CurseDetector]  %-25s ⚠ CURSED — suspiciously cheap for +%d atk\n",
                    weapon.getName(), weapon.getAttackBonus());
        } else {
            System.out.printf("  [CurseDetector]  %-25s   clean\n", weapon.getName());
        }
    }

    @Override
    public void visit(Potion potion) {
        boolean cursed = potion.getHealing() < 0;
        if (cursed) {
            cursedCount++;
            System.out.printf("  [CurseDetector]  %-25s ⚠ CURSED — negative healing!\n",
                    potion.getName());
        } else {
            System.out.printf("  [CurseDetector]  %-25s   safe potion\n", potion.getName());
        }
    }

    @Override
    public void visit(Scroll scroll) {
        boolean dangerous = scroll.getSpellName().toLowerCase().contains("doom")
                || scroll.getSpellName().toLowerCase().contains("void")
                || scroll.getSpellName().toLowerCase().contains("death");
        if (dangerous) {
            cursedCount++;
            System.out.printf("  [CurseDetector]  %-25s ⚠ DANGEROUS spell: \"%s\"\n",
                    scroll.getName(), scroll.getSpellName());
        } else {
            System.out.printf("  [CurseDetector]  %-25s   benign scroll\n", scroll.getName());
        }
    }

    @Override
    public void visit(Ring ring) {
        boolean cursed = ring.getMagicBonus() >= 15;
        if (cursed) {
            cursedCount++;
            System.out.printf("  [CurseDetector]  %-25s ⚠ BINDING CURSE — magic +%d too strong\n",
                    ring.getName(), ring.getMagicBonus());
        } else {
            System.out.printf("  [CurseDetector]  %-25s   safe ring\n", ring.getName());
        }
    }

    @Override
    public void visit(Armor armor) {
        System.out.printf("  [CurseDetector]  %-25s   armor clear\n", armor.getName());
    }
}