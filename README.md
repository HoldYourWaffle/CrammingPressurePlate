## CrammingPressurePlate

CrammingPressurePlate is a mod for Minecraft Forge 1.12.2 and beyond that adds a new type of weighted pressure plate: the ultra* weighted pressure plate.

### Configuration
**Spread out power increase**<br>
The [normal](https://minecraft.gamepedia.com/Pressure_Plate#behavior) weighted pressure plate behavior.
The outgoing power level gradually increases while nearing the `maxEntityCramming` limit. It follows the formula:

`PowerLevel = mobsOnPressurePlate / maxEntityCramming * 15`
<br><br>
**Immediate power increase (default)**<br>
The pressure plate only outputs a redstone signal of 15 when there are a `maxEntityCramming` amount of entities on the pressure plate.

### Recipe
![Recipe is 2 redstone dusts next to each other](https://i.imgur.com/EBpQCTN.png)

###### * - The name 'ultra' suggest that it's 'heavier' than the vanilla 'heavy-weighted pressure plate'. However, on the standard settings this isn't the case as `maxEntityCramming` is 24 by default and the 'weight' of a heavy-weighted pressure plate is 150.

