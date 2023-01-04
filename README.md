# More Apoli

More Apoli is a mod for datapack developers which adds more powers to
[Apoli](https://github.com/apace100/apoli).

### Usage
[Read the documentation!](https://github.com/MacaylaMarvelous81/MoreApoli/tree/master/docs)

This mod's namespace is `more_apoli`. Here's an example
[Origins](https://github.com/apace100/origins-fabric) power which uses
the `action_on_item_drop` power:

```json
{
  "type": "more_apoli:action_on_item_drop",
  "name": "Item Drop Action Test",
  "description": "Drop an item, see what happens ;)",
  "item_condition": {
    "type": "origins:food"
  },
  "entity_action": {
    "type": "origins:damage",
    "amount": 4,
    "source": {
      "name": "onFire",
      "fire": true,
      "bypasses_armor": false
    }
  }
}
```

In this case, players using an origin with this power would take 4 damage
(2 hearts) when they drop a food item.