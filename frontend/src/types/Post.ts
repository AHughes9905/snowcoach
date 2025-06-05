import type { Reply } from "./Reply.ts";

export interface Post {
    id: number;
    title: string;
    level: number;
    topic: string;
    body?: string;
    mediaUrl?: string;
    visibility?: string;
    claimer?: string;
    [key: string]: any;
    replies?: Reply[];
}