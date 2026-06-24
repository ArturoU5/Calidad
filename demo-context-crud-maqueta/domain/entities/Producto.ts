// PASO 2 - Definir la entidad del dominio.

export interface Producto {
  id: string;
  nombre: string;
  precio: number;
}

export type ProductoFormData = Omit<Producto, 'id'>;
