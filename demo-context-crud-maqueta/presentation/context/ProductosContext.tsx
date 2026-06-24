import { createContext, PropsWithChildren, useContext } from 'react';
import { Producto, ProductoFormData } from '../../domain/entities/Producto';

// TODO 2 - Definir el contrato del Context.

interface ProductosContextValue {
  productos: Producto[];
  crear: (datos: ProductoFormData) => void;
  actualizar: (id: string, datos: ProductoFormData) => void;
  eliminar: (id: string) => void;
}

// TODO 3 - Crear el Context.

const ProductosContext = createContext<ProductosContextValue | undefined>(undefined);

// TODO 4 - Implementar el Provider en clase.

export function ProductosProvider({ children }: PropsWithChildren) {
 
  return <>{children}</>;
}

// TODO 7 - Usar este hook cuando el Provider ya esté implementado.

export function useProductos() {
  const context = useContext(ProductosContext);
  if (!context) {
    throw new Error('useProductos debe utilizarse dentro de ProductosProvider');
  }
  return context;
}
