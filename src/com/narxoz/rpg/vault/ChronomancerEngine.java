package com.narxoz.rpg.vault;

import com.narxoz.rpg.artifact.*;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.HeroMemento;
import com.narxoz.rpg.memento.Caretaker;

import java.util.List;

/**
 * Orchestrates the Chronomancer's Vault demo run.
 */
public class ChronomancerEngine {

    /**
     * Runs the vault sequence for the supplied party.
     *
     * @param party the heroes entering the vault
     * @return a summary of what happened during the run
     */
    public VaultRunResult runVault(List<Hero> party) {

        int artifactsAppraised = 0;
        int mementosCreated    = 0;
        int restoredCount      = 0;

        // ----------------------------------------------------------------
        // Build a shared vault inventory  (≥ 5 artifacts)
        // ----------------------------------------------------------------
        Inventory vaultInventory = new Inventory();
        vaultInventory.addArtifact(new Weapon ("Shadowblade",        80,  6, 14));
        vaultInventory.addArtifact(new Armor  ("Iron Bulwark",       60, 12,  9));
        vaultInventory.addArtifact(new Potion ("Elixir of Vitality", 25,  2, 40));
        vaultInventory.addArtifact(new Ring   ("Ring of the Void",   50,  1, 18));
        vaultInventory.addArtifact(new Scroll ("Scroll of Doom",     20,  1, "Doom Nova"));
        vaultInventory.addArtifact(new Weapon ("Rusty Dagger",       15,  3,  5));

        System.out.println("\n══════════════════════════════════════════════════");
        System.out.println("  THE CHRONOMANCER'S VAULT — artifact appraisal");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("  Vault contains " + vaultInventory.size() + " artifacts.\n");

        // ── Visitor 1: GoldAppraiser ──────────────────────────────────
        System.out.println("▶ Running GoldAppraiser...");
        GoldAppraiser goldAppraiser = new GoldAppraiser();
        vaultInventory.accept(goldAppraiser);
        System.out.println("  Total estimated value: " + goldAppraiser.getTotalGold() + " gold\n");
        artifactsAppraised += vaultInventory.size();

        // ── Visitor 2: EnchantmentScanner ────────────────────────────
        System.out.println("▶ Running EnchantmentScanner...");
        EnchantmentScanner scanner = new EnchantmentScanner();
        vaultInventory.accept(scanner);
        System.out.println("  Enchanted items found: " + scanner.getEnchantedCount() + "\n");
        artifactsAppraised += vaultInventory.size();

        // ── Visitor 3: CurseDetector ──────────────────────────────────
        System.out.println("▶ Running CurseDetector...");
        CurseDetector detector = new CurseDetector();
        vaultInventory.accept(detector);
        System.out.println("  Cursed items detected: " + detector.getCursedCount() + "\n");
        artifactsAppraised += vaultInventory.size();

        // ── Visitor 4: WeightCalculator (Open/Closed proof) ───────────
        System.out.println("▶ Running WeightCalculator  [Open/Closed extension — no artifact files modified]...");
        WeightCalculator weightCalc = new WeightCalculator();
        vaultInventory.accept(weightCalc);
        System.out.println("  Total carry weight: " + weightCalc.getTotalWeight() + " kg\n");
        artifactsAppraised += vaultInventory.size();

        // ----------------------------------------------------------------
        // Memento demo for each hero
        // ----------------------------------------------------------------
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("  HERO VAULT EVENTS — snapshot & rewind");
        System.out.println("══════════════════════════════════════════════════");

        for (Hero hero : party) {
            System.out.println("\n─── " + hero.getName() + " enters the vault ───");
            System.out.println("  State BEFORE: " + hero);

            // Save snapshot
            Caretaker caretaker = new Caretaker();
            HeroMemento snapshot = hero.createMemento();
            caretaker.save(snapshot);
            mementosCreated++;
            System.out.println("  ◉ Snapshot saved  (caretaker holds " + caretaker.size() + " memento)");

            // Vault trap fires
            System.out.println("  ⚡ Vault trap! Hero loses 30 HP, 20 mana, 50 gold...");
            hero.takeDamage(30);
            hero.spendMana(Math.min(20, hero.getMana()));
            hero.spendGold(Math.min(50, hero.getGold()));
            System.out.println("  State AFTER trap: " + hero);

            // Rewind via memento
            HeroMemento savedMemento = caretaker.undo();
            hero.restoreFromMemento(savedMemento);
            restoredCount++;
            System.out.println("  ↩ Rewind applied  (caretaker now holds " + caretaker.size() + " mementos)");
            System.out.println("  State AFTER rewind: " + hero);
        }

        System.out.println();
        return new VaultRunResult(artifactsAppraised, mementosCreated, restoredCount);
    }
}