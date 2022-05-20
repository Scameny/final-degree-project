import {init} from './appFetch';
import * as userService from './userService';
import * as clasificadorService from './clasificadorService';
import * as conjuntoService from './conjuntoService';


export {default as NetworkError} from "./NetworkError";

export default {init, userService, clasificadorService, conjuntoService};
