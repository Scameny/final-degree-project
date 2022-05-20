import {configFile, appFetch} from './appFetch';


export const subirArchivo = (file, nombre, descripcion, onSuccess, onErrors) => 
    appFetch('/clasificacion/uploadFile', configFile('POST', file, nombre, descripcion), onSuccess, onErrors);
