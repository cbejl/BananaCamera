## **BananaCamera**
**плагин добавляющий камеру, телепортирующуюся по значимым локациям сервера.**
<sub>как на майншилде.</sub>

Для работы плагина необходимы:
- 1 игрок-камера.
- 1 минута настройка конфига.

***config.yml:***
```yaml
cameraName: "NickName"  - имя игрока-камеры
period: 10              - время в секундах, через которое будет происходить смена локации
```

***locations.yml:***
```yaml
locations:
  - title: "Title"      - основная строка отображаемая при перемещении на локацию
    subtitle: "sub"     - суб-строка отображаемая при перемещении на локацию
    world: "world"      - название папки измерения в которое телепортируется камера
    xyz: [ 0.0,0.0,0.0 ] - координаты камеры
    rotation: [ 0.0,0.0 ] - поворот камеры  
```

---
<sub>translate:<sub>
## **BananaCamera**
**a plugin that adds a camera that teleports to significant server locations.**

For the plugin to work, you need:
- 1 player is a camera.
- 1 minute configuration of the config.

***config.yml:***
```yaml
cameraName: "NickName"  - nickname of camera-player
period: 10              - the time in seconds after which the location will change
```

***locations.yml:***
```yaml
locations:
  - title: "Title"      - a main line of text displayed on the screen
    subtitle: "sub"     - a second line of text displayed on the screen
    world: "world"      - the name of the dimension folder to which the camera is teleported
    xyz: [ 0.0,0.0,0.0 ] - camera coordinates
    rotation: [ 0.0,0.0 ] - camera rotation
```
