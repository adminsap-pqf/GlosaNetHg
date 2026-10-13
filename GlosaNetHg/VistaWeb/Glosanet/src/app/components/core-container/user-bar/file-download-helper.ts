import {saveAs} from 'file-saver/FileSaver';
/* Dev Tools */
import {DEBUG} from "../../../settings/app.settings";

/**
 * Saves a file by opening file-save-as dialog in the browser
 * using file-save library.
 * @param blobContent file content as a Blob
 * @param fileName name file should be saved as
 */
export const saveFile = (blobContent: Blob, fileName: string) => {
  const blob = new Blob([blobContent], {type: 'application/octet-stream'});
  saveAs(blob, fileName);
};

/**
 * Derives file name from the String
 * by looking inside content-disposition
 * @param res String
 */
export const getFileNameFromResponseContentDisposition = (res: String) => {
  const matches = res.split("/");
  DEBUG && console.log(matches);
  const fileName = matches[matches.length - 1];
  DEBUG && console.log(fileName);
  return fileName;
};
