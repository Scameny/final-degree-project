import {config, appFetch} from './appFetch';


export const verDetallesConjunto = (id, onSuccess) => 
    appFetch(`/conjunto/${id}`, config('GET'), onSuccess);

export const listaConjuntos = (page, onSuccess) => 
    appFetch(`/conjunto/lista?page=${page}`, config('GET'), onSuccess);

export const verCuentasConjunto = (id, onSuccess) =>
    appFetch(`/conjunto/${id}/cuentas`, config('GET'), onSuccess);

export const verInteraccionesConjunto = (id, onSuccess) =>
    appFetch(`/conjunto/${id}/interacciones`, config('GET'), onSuccess);

export const verCuentasConFiltroHumano = (id, porcentajeHumano, onSuccess) =>
    appFetch(`/conjunto/${id}/cuentas/filtro?porcentajeHumano=${porcentajeHumano}`,
     config(`GET`), onSuccess);

export const verCuentasConFiltroBot = (id, porcentajeBot, onSuccess) =>
     appFetch(`/conjunto/${id}/cuentas/filtro?porcentajeBot=${porcentajeBot}`,
      config(`GET`), onSuccess);

export const verCuentasConFiltro = (id, porcentajeHumano, porcentajeBot, onSuccess) =>
      appFetch(`/conjunto/${id}/cuentas/filtro?porcentajeHumano=${porcentajeHumano}&porcentajeBot=${porcentajeBot}`,
       config(`GET`), onSuccess);
  
