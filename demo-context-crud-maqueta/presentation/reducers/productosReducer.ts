import { Producto } from '../../domain/entities/Producto';

// TODO 8 - Definir acciones del reducer.

export type ProductoAction =
  | { type: 'CREAR'; payload: Producto }
  | { type: 'ACTUALIZAR'; payload: Producto }
  | { type: 'ELIMINAR'; payload: string };

// TODO 9 - Implementar el reducer en clase.

export function productosReducer(state: Producto[], action: ProductoAction): Producto[] {
  switch (action.type) {
    case 'CREAR':
      // Completar con spread:
      // return [...state, action.payload];
      return state;

    case 'ACTUALIZAR':
      // Completar con map:
      // return state.map(...)
      return state;

    case 'ELIMINAR':
      // Completar con filter:
      // return state.filter(...)
      return state;

    default:
      return state;
  }
}
