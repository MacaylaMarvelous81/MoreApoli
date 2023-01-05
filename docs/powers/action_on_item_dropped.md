# Action on Item Dropped

The `action_on_item_dropped` power enables you to trigger an action after a
player drops an item through a power. This is **only** triggered on players,
not other entities.

### Fields

| Field                | Type                                                                                | Default  | Description                                                                                           |
|----------------------|-------------------------------------------------------------------------------------|----------|-------------------------------------------------------------------------------------------------------|
| `item_condition`     | [Item Condition](https://origin.readthedocs.io/en/latest/item_condition_types)      | optional | If specified, the actions will only execute if this condition is fulfilled by the item after dropped. |
| `entity_action`      | [Entity Action](https://origins.readthedocs.io/en/latest/types/entity_action_types) | optional | If specified, this action will be executed on the player after they drop an item.                     |
| `item_action`        | [Item Action](https://origins.readthedocs.io/en/latest/types/item_action_types)     | optional | If specified, this action will be executed on **a copy** of the stack of dropped items.               |
| `item_entity_action` | [Entity Action](https://origins.readthedocs.io/en/latest/types/entity_action_types) | optional | If specified, this action will be executed on the entity corresponding to the dropped item(s).        |

### Example

The following example power deals 4 damage (2 hearts) to the player when
they drop a food item.

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