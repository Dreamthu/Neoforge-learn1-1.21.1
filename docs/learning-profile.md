# Learning Profile

This document records the learning context for this project. It is not a formal spec. Its purpose is to help future code changes come with explanations that match the current learning stage.

## Student Background

- Year: first-year university student.
- Known languages: C++ and Java basics.
- Current goal: learn Minecraft mod development while also improving Java understanding.
- Main difficulty: tutorials show what to type, but often do not explain why Minecraft or NeoForge expects that structure.

## Collaboration Preference

- Primary task: help understand code and Minecraft/NeoForge concepts.
- Secondary task: write or modify code when explicitly asked.
- When modifying code, prefer simple and direct implementations over highly abstract or "professional" patterns.
- Explanations should focus on why a method, class, event, tag, registry, or JSON file is needed.
- When possible, connect new code to similar vanilla Minecraft behavior.

## Current Project Context

The project is a NeoForge 1.21.1 mod named `learn1mod`.

Implemented or partially implemented systems include:

- Custom items, tools, armor, and horse armor.
- Custom blocks and block variants.
- Hammer area mining.
- Crop block and seeds.
- Custom potion and mob effect.
- Custom enchantments.
- Jukebox song.
- Paintings.
- Datagen for recipes, loot tables, tags, models, and registry-backed data.

## Concepts To Build Up

Important Minecraft concepts:

- `Item` vs `ItemStack`: item type vs a concrete stack in the world/inventory.
- `Block` vs `BlockState`: block type vs a placed block's current state.
- `Level` / `ServerLevel`: world access, and why world-changing logic should happen on the server.
- `ResourceLocation`: the unique id format like `learn1mod:frost_hooves`.
- Tags: named groups of items, blocks, enchantments, etc.
- Events: hooks fired when something happens in the game.
- Registries: Minecraft's global lists of known game objects.
- Datagen: Java code that produces JSON resources.

Important Java concepts:

- Inheritance and method overriding.
- Static fields used for registration handles.
- Generic types such as `DeferredItem<Item>`.
- Lambdas and suppliers.
- Records and data-oriented classes.
- Why some APIs use `Holder`, `ResourceKey`, or `TagKey`.

## Current Learning Strategy

For each new feature, try to answer these questions before writing code:

1. What kind of thing is being added: item, block, enchantment, event behavior, JSON resource, or tag?
2. When should the behavior happen?
3. Does it change the world? If yes, it should usually run on the server.
4. Does Minecraft need a registered id for it?
5. Does it need generated JSON resources?
6. Is there a vanilla feature with similar behavior?

## Recent Example: Frost Hooves

Goal: make horse armor work like Frost Walker, so a horse can walk on water.

Main pieces:

- Register an enchantment named `learn1mod:frost_hooves`.
- Restrict it to horse armor through an item tag.
- Make the custom bismuth horse armor enchantable, because vanilla `AnimalArmorItem` disables enchanting.
- On entity tick, check whether a non-player living entity has enchanted body armor.
- If yes, turn nearby water below the entity into `FROSTED_ICE`.
- Schedule a block tick so the frosted ice melts later like vanilla Frost Walker ice.

## Notes For Future Work

- Prefer explaining code changes in plain language before or after editing.
- If a feature can be implemented in both a data-driven way and an event-driven way, explain the tradeoff.
- Keep code readable for a learner unless there is a clear reason to introduce a stronger abstraction.
- When introducing a new Minecraft concept, add a short note here if it is likely to come up again.
