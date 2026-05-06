package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.vault.ChronomancerEngine;
import com.narxoz.rpg.vault.VaultRunResult;

import java.util.List;

/**
 * Entry point for Homework 9 -- Chronomancer's Vault: Visitor + Memento.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 9 Demo: Visitor + Memento ===\n");

        // 1. Two heroes with different starting states
        Hero warrior = new Hero(
                "Aric the Warrior",
                120,   // hp
                30,    // mana
                18,    // attackPower
                12,    // defense
                200,   // gold
                null   // empty inventory
        );

        Hero mage = new Hero(
                "Lyra the Mage",
                70,    // hp
                150,   // mana
                8,     // attackPower
                5,     // defense
                120,   // gold
                null
        );

        List<Hero> party = List.of(warrior, mage);

        // 2. Run the vault -- visitors, mementos, rewind all happen inside
        ChronomancerEngine engine = new ChronomancerEngine();
        VaultRunResult result = engine.runVault(party);

        // 3. Print final summary
        System.out.println("==================================================");
        System.out.println("  VAULT RUN COMPLETE");
        System.out.println("==================================================");
        System.out.println("  " + result);
        System.out.println("=== End of Demo ===");
    }
}