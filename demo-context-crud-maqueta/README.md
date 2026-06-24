# Maqueta — Context API + CRUD en memoria

Proyecto para trabajar en clase paso a paso.

Esta maqueta ya tiene la interfaz lista, pero todavía no tiene conectado:

- Context API real.
- Provider global.
- `useReducer`.
- CRUD en memoria.



## Ejecutar

```bash
npm install
npm start
```

## Qué ya está listo

- Pantalla principal en `app/index.tsx`.
- Formulario visual.
- Lista con `FlatList`.
- Tarjeta de producto.
- Entidad `Producto`.
- Archivos preparados para Context y reducer.

## Qué deben implementar en clase

1. Crear el `ProductosProvider`.
2. Conectar `useReducer`.
3. Implementar las funciones `crear`, `actualizar` y `eliminar`.
4. Envolver la app con el Provider en `app/_layout.tsx`.
5. Consumir `useProductos()` desde `app/index.tsx`.
6. Conectar el formulario con `crear` y `actualizar`.
7. Conectar el botón eliminar con `eliminar`.
8. Completar el reducer con `spread`, `map` y `filter`.

## Archivos principales

```text
app/_layout.tsx                         Aquí se agregará el Provider
app/index.tsx                           Pantalla principal
domain/entities/Producto.ts             Entidad del dominio
presentation/context/ProductosContext.tsx Context API pendiente de implementar
presentation/reducers/productosReducer.ts Reducer pendiente de completar
presentation/hooks/useProductoForm.ts   Estado local del formulario
presentation/components/ProductoForm.tsx Formulario visual
presentation/components/ProductoItem.tsx Tarjeta visual
```
