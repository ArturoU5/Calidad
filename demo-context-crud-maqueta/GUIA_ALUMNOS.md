# Guía alumnos — Implementar Context API paso a paso

## Objetivo

Convertir una maqueta visual en una aplicación con estado global usando Context API y `useReducer`.

## Orden sugerido

1. Revisar la entidad `Producto`.
2. Revisar la pantalla `app/index.tsx`.
3. Implementar el reducer.
4. Implementar el Provider.
5. Envolver la app con el Provider.
6. Consumir el Context desde la pantalla.
7. Conectar crear, editar y eliminar.

## Flujo esperado

```text
Pantalla
  ↓
useProductos()
  ↓
Context API
  ↓
crear / actualizar / eliminar
  ↓
dispatch(action)
  ↓
productosReducer
  ↓
nuevo estado
  ↓
la pantalla se actualiza
```

## Recordatorio

Los datos viven en memoria. Si se reinicia la app, se pierde la información creada durante la ejecución.
