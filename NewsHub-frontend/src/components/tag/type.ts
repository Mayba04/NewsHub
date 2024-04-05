export interface ITagSearch{
    name: string,
    page: number,
    size: number
}

export interface IGetTags {
    content: ITagItem[],
    totalPages: number,
    totalElements: number,
    number: number
}

export interface ITagItem {
    id: number;
    name: string;
}

export interface ITagCreate 
{
    name: string;
}


export interface ITagEdit {
    id: number;
    name: string;
}