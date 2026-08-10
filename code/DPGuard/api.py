from fastapi import FastAPI, UploadFile, File
from DPGuard import *
from Model import ResNetBinary
from PIL import Image
import os
import uuid
import __main__

# Bestaande PyTorch-model laden
__main__.ResNetBinary = ResNetBinary

app = FastAPI()

detector_gpt = DPGuard(binary_model_path="binary_model/binary_rn101_ep6.pth", mllm_model="gpt-4o")

@app.post("/analyze")
async def analyze(file: UploadFile = File(...)):
    # Unieke tijdelijke bestandsnaam
    extension = os.path.splitext(file.filename)[1]
    filename = f"{uuid.uuid4()}{extension}"
    image_path = f"uploads/{filename}"

    # Screenshot opslaan
    with open(image_path, "wb") as buffer:
        buffer.write(await file.read())

    # Afbeelding naar RGB omzetten
    try:
        with Image.open(image_path) as img:
          if img.mode != "RGB":
              img = img.convert("RGB")
              img.save(image_path)

        # DPGuard uitvoeren
        result = detector_gpt.detect(image_path)

        # Resultaat teruggeven
        return {
            "filename": file.filename,
            "result": result
        }

    except Exception as e:
        print("FOUT:")
        print(e)

        return {
            "error": str(e)
        }

    finally:
        # Tijdelijk bestand verwijderen
        if os.path.exists(image_path):
            os.remove(image_path)

    