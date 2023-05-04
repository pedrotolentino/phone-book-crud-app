import { Contact } from "./contact";

export interface ContactWrapper {
    contacts: Contact[],
    pageNumber: number,
    pageSize: number,
    totalElements: number,
    totalPages: number
}
