# Step Height

The `step_height` power enables easy modification to an entity's step height.
An entity's step height determines whether they are able to walk up a certain
height of blocks. As an example, the player uses the default step height of
living entities, which is 0.6. Players can walk on lanterns, carpets, slabs,
etc., but not full blocks. In contrast, horses have a step height of 1, and
_can_ walk a full block.

The step height is not guaranteed to be modified in the case that something
else modifies it, such as another `step_height` applied somehow or if it
is modified by another mod, like
[Pehkui](https://www.curseforge.com/minecraft/mc-mods/pehkui).

| Field    | Type                                                                     | Default | Description                        |
|----------|--------------------------------------------------------------------------|---------|------------------------------------|
| `height` | [Float](https://origins.readthedocs.io/en/latest/types/data_types/float) | 0.6     | The new step height of the entity. |

### Example

```json
{
  "type": "more_apoli:step_height",
  "name": "Step Height Test",
  "description": "you can step really high",
  "height": 1.6
}
```

Entities with this example power have their step height at 1.6.