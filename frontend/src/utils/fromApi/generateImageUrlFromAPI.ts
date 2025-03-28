import { Image } from "../types/ApiTypes";

export function generateImageURL(image : Image): string {
  const imageSrc = `data:${image.contentType};base64,${image.data}`;

  return imageSrc
}