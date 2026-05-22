<div align="center">
# 🌻 Plants vs Zombies
 
**A Java + JavaFX tower defense game inspired by the classic PopCap title.**
 
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-21-1B6AC6?style=flat-square&logo=java&logoColor=white)
![60 FPS](https://img.shields.io/badge/60_FPS-AnimationTimer-brightgreen?style=flat-square)
![Status](https://img.shields.io/badge/Status-Complete-success?style=flat-square)
 
</div>
---
 
## 🎮 About
 
Plants vs Zombies is a desktop tower-defense game built in **Java 21 with JavaFX**. The player places plants on a 5×9 grid to hold off waves of incoming zombies — including ones that drop from the sky. Survive all 3 waves within 90 seconds and clear the remaining zombies to win.
 
The game runs at a fixed **60 FPS** using JavaFX's `AnimationTimer`, with a clean separation between game logic, rendering, and input.
 
---
 
## 🕹️ How to Play
 
1. Launch the game — you'll see the **main menu**.
2. Press **Play** to start a new game.
3. Click a **plant card** in the inventory to select it.
4. Click a **tile** on the map to place the plant (costs sun ☀️).
5. **Survive** all 3 waves — if any zombie reaches the left edge, you lose.
6. **Win** by lasting 90 seconds and eliminating all remaining zombies.
> ⚠️ You can't place a plant on a tile already occupied by another plant or a zombie.
 
---
 
## 🌱 Plants
 
| Plant | Cost ☀️ | HP | Role | Special ability |
|---|---|---|---|---|
| 🟢 **Peashooter** | 10 | 100 | Attacker | Shoots peas down its row every second |
| 🧱 **Wall-nut** | 12 | 10 000 | Tank | Melee-attacks adjacent zombies; absorbs huge damage |
| 🌻 **Sunflower** | 8 | 200 | Support | **Heals all 8 neighbouring plants** every 5 seconds |
| 🍒 **Cherry Bomb** | 10 | 1 | AOE | Explodes when a zombie touches it; damages all zombies in radius |
| ❄️ **Snow Pea** | 6 | 100 | Hybrid | Shoots ice peas (slows zombies for 2 s) **and** heals orthogonal neighbours |
 
> 💡 **Sunflower heals neighbours** instead of generating sun — keep her surrounded by your front-line plants.
 
> 💡 **Snow Pea is a dual-role plant** — it both shoots and heals, making it the most versatile option.
 
---
 
## 🧟 Zombies
 
| Zombie | HP | Damage | Speed | Special |
|---|---|---|---|---|
| 🧟 **Normal Zombie** | 70 | 10 | 1.0 | Standard walker from the right edge |
| 🪂 **Parachute Zombie** | 45 | 20 | 1.5 | **Drops from above** into a random column (not the first two); faster and hits harder |
 
> ⚠️ **Parachute Zombies bypass your front line** — they can land anywhere behind your defenses. Place plants strategically in the back rows too.
 
---
 
## 🌊 Wave System
 
The game lasts **90 seconds** divided into 3 phases of 30 seconds each. Zombies spawn continuously, but escalate over time:
 
| Phase | Duration | Spawn interval | Parachute chance |
|---|---|---|---|
| **Wave 1** | 0–30 s | every 10 s | 20% |
| **Wave 2** | 30–60 s | every 7 s | 30% |
| **Wave 3** | 60–90 s | every 4 s | 40% |
 
After 90 seconds no more zombies spawn. Eliminate all remaining zombies to win.
 
---
 
## ☀️ Sun Economy
 
Sun is your currency for buying plants. You earn it two ways:
 
- **Passively** — +1 sun per second, automatically
- **On kill** — +5 sun for every zombie you eliminate
Starting sun is **0**, so Sunflower and Snow Pea (cost 6–8) are good early picks to build up your economy while you set defenses.
 
---
 
## 🗂️ Project Structure
 
```
src/main/java/sk/fri/uniza/pvz/
│
├── GameController.java          # JavaFX Application entry point; manages menu ↔ game transitions
├── Main.java
│
├── entities/
│   ├── Entity.java              # Base class (position, sprite, health)
│   ├── HealthBar.java
│   ├── plants/
│   │   ├── Plant.java           # Abstract plant with cooldown-based update loop
│   │   ├── PlantType.java       # Enum: cost, HP, damage multiplier, ability power, lifetime
│   │   ├── purpose/             # Interfaces: Shooter, Healer, MeleeAttacker
│   │   ├── projectile/          # NormalPea, IcePea, Projectile, ProjectileType
│   │   └── types/               # PeaShooter, Wallnut, SunFlower, CherryBomb, SnowPea
│   └── zombies/
│       ├── Zombie.java          # Abstract zombie with WALKING / ATTACKING state machine
│       ├── ZombieType.java      # Enum: HP, damage, speed, attack cooldown
│       ├── ZombieState.java
│       └── types/               # NormalZombie, ParachuteZombie
│
├── gamelogic/
│   ├── EntityManager.java       # Holds and updates all active plants, zombies, projectiles
│   ├── WaveManager.java         # Phase-based zombie spawner (3 phases × 30 s)
│   ├── GameStateManager.java    # Detects win (waves done + no zombies) or loss (zombie at x=0)
│   └── GameState.java           # Enum: RUNNING, WON, LOST
│
├── inputs/
│   └── InputHandler.java        # Click → card select / plant placement with exception handling
│
├── map/
│   ├── Map.java                 # 5×9 tile grid; plant placement and lookup
│   └── Tile.java
│
├── menu/
│   └── MainMenu.java            # FXML-based main menu; shows result of previous game
│
├── player/
│   ├── SunCount.java            # Passive sun gain + zombie-kill reward + spend validation
│   ├── Inventory.java           # Plant card selection and cooldown management
│   └── PlantCard.java
│
├── renderers/
│   ├── StaticRenderer.java      # Renders map tiles and inventory (once)
│   ├── DynamicRenderer.java     # Re-renders entities every frame
│   └── HudRenderer.java         # Sun counter, wave indicator, warning messages
│
└── utils/
    ├── CollisionManager.java    # Projectile–zombie and plant–zombie collision detection
    ├── Constants.java           # FPS (60), resolution (900×600), time→ticks conversion
    └── ImageLoader.java
```
 
---
 
## ⚙️ Architecture Highlights
 
**Game loop** — `AnimationTimer` in `GameController` calls every frame (60 FPS):
```
waveManager.update()  →  entityManager.updateAll()  →  collisionManager.checkCollisions()
→  sunCount.update()  →  hudRenderer.update()  →  dynamicRenderer.render()
→  entityManager.removeAllDead()
```
 
**Plant behaviour** — All plants extend the abstract `Plant` class and implement one or more purpose interfaces (`Shooter`, `Healer`, `MeleeAttacker`). Each plant overrides `onUpdate(EntityManager)` and manages its own cooldown timer.
 
**Exception-driven placement** — `InputHandler` throws typed exceptions (`NotEnoughSunException`, `PlantAlreadyOnTileException`, `TileOccupiedByZombieException`) that the HUD displays as in-game warnings.
 
**Zombie state machine** — Each zombie is either `WALKING` (moving left each frame) or `ATTACKING` (locked onto a plant). The `CollisionManager` triggers the transition.
 
---
 
## 🚀 Getting Started
 
**Prerequisites:** Java 21, JavaFX 21
 
```bash
git clone https://github.com/LukasKocurek/PlantsVsZombies.git
cd PlantsVsZombies
```
 
Run with your IDE (IntelliJ IDEA recommended) or manually with the JavaFX SDK on the module path.
 
---
 
## 📄 License
 
Fan-made educational project. *Plants vs. Zombies is a trademark of Electronic Arts / PopCap Games.*
 
