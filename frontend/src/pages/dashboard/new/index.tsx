
import { ChangeEvent, useState, useContext } from "react";

import { AuthContext } from "../../../contexts/authContext";

import { Container } from "../../../components/container";
import { DashboardHeader } from "../../../components/panelHeader";

import { FiUpload, FiTrash } from 'react-icons/fi';

import { useForm } from 'react-hook-form';
import { Input } from "../../../components/input";
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';

import { v4 as uuidV4 } from 'uuid';

import { storage, db } from '../../../services/firebaseConnection';
import {
    ref,
    uploadBytes,
    getDownloadURL,
    deleteObject
} from 'firebase/storage';

import { addDoc, collection} from 'firebase/firestore';
import toast from "react-hot-toast";

const schema = z.object({
    name: z.string().nonempty("O nome é obrigatório."),
    model: z.string().nonempty("O modelo é obrigatório."),
    year: z.string().nonempty("O ano é obrigatório."),
    km: z.string().nonempty("O KM é obrigatório."),
    price: z.string().nonempty("O preço é obrigatório."),
    city: z.string().nonempty("A cidade é obrigatória."),
    whatsapp: z.string().min(1, "O Telefone é obrigatório.").refine((value) => /^(\d{10,11})$/.test(value), {
        message: "Número de telefone inválido."
    }),
    description: z.string().nonempty("A descrição é obrigatória.")
})

type FormData = z.infer<typeof schema>;

interface IImageItemProps{
    uid: string;
    name: string;
    previewUrl: string;
    url: string;
}

export function New() {

    const [carImages, setCarImages] = useState<IImageItemProps[]>([]);

    const { user } = useContext(AuthContext);

    const { register, handleSubmit, formState: { errors }, reset } = useForm<FormData>({
        resolver: zodResolver(schema),
        mode: "onChange"
    })

    function onSubmit(data : FormData){

        if(carImages.length === 0) {
            toast.error("Envie alguma imagem deste carro!")
            return;
        }

        const carListImages = carImages.map( car => {
            return{
                uid: car.uid,
                name: car.name,
                url: car.url
            }
        })

        addDoc(collection(db, "cars"), {
            name: data.name.toUpperCase(),
            model: data.model,
            whatsapp: data.whatsapp,
            city: data.city,
            year: data.year,
            km: data.km,
            price: data.price,
            description: data.description,
            created: new Date(),
            ownder: user?.name,
            uid: user?.uid,
            images: carListImages
        }).then(() => {
            toast.success("Carro cadastrado com sucesso!")
            console.log("cadastrado com sucesso");
            reset();
            setCarImages([]);
        }).catch((error) => {
            toast.error("Não foi possível cadastrar o carro!")
            console.log(error);
        })

    }

    async function handleFile(e : ChangeEvent<HTMLInputElement>){

        if(e.target.files && e.target.files[0]) {
            const image = e.target.files[0];

            if(image.type === 'image/jpeg' || image.type === 'image/png'){
                await handleUpload(image);
            } else {
                alert("Envia imagem jpeg ou png");
                return;
            }
        }

    }

    async function handleUpload(image : File) {

        if(!user?.uid) {
            return;
        }

        const currentUid = user?.uid;
        const uidImage = uuidV4();

        const uploadRef = ref(storage, `images/${currentUid}/${uidImage}`);
        uploadBytes(uploadRef, image)
            .then((snapshot) => {
                getDownloadURL(snapshot.ref).then((dowsloadURL) => {
                        const imageItem = {
                            name: uidImage,
                            uid: currentUid,
                            previewUrl: URL.createObjectURL(image),
                            url: dowsloadURL,
                        }
                        setCarImages((images) => [...images, imageItem]);
                    })
            })

    }

    async function handleDeleteImage(item : IImageItemProps) {
        const imagePath = `images/${item.uid}/${item.name}`;
        const imageRef = ref(storage, imagePath);
        try {
            await deleteObject(imageRef)
            setCarImages(carImages.filter((car) => car.url !== item.url));
        } catch(err) {
            console.log(err)
        }
    }

    return(
        <Container>
            <DashboardHeader />
            <div
                className="w-full bg-white p-3 rounded-lg flex flex-col sm:flex-row items-center gap-2"
            >
                <button
                    className="border-2 w-48 rounded-lg flex items-center justify-center cursor-pointer border-gray-600 h-32 md:w-48"
                >
                    <div
                        className="absolute cursor-pointer"
                    >
                        <FiUpload size={30} color="#000" />
                    </div>
                    <div
                        className="cursor-pointer"
                    >
                        <input 
                            className="opacity-0 cursor-pointer"
                            type="file" 
                            accept="image/*"
                            onChange={handleFile}
                        />
                    </div>
                </button>

                {carImages.map( item => (
                    <div
                        key={item.name}
                        className="w-full h-32 flex items-center justify-center relative"
                    >
                        <button
                            onClick={() => handleDeleteImage(item)}
                            className="absolute"
                        >
                            <FiTrash size={28} color="#FFF"/>
                        </button>
                        <img 
                            src={item.previewUrl}
                            className="rounded-lg w-full h-32 object-cover"
                            alt="foto do carro"
                        />
                    </div>
                ))}
            </div>

            <div
                className="w-full bg-white p-3 rounded-lg flex flex-col sm:flex-row items-center gap-2 mt-2"
            >
                <form
                    className="w-full"
                    onSubmit={handleSubmit(onSubmit)}
                >
                    <div className="mb-3">
                        <p className="mb-2 font-medium">
                            Nome do carro
                        </p>
                        <Input
                            type="text"
                            register={register}
                            name="name"
                            error={errors.name?.message}
                            placeholder="Ex.: Audi TT"
                        />
                    </div>

                    <div className="mb-3">
                        <p className="mb-2 font-medium">
                            Modelo
                        </p>
                        <Input
                            type="text"
                            register={register}
                            name="model"
                            error={errors.model?.message}
                            placeholder="Ex.: 2.0 Turbo"
                        />
                    </div>
                    <div className="flex w-full mb-3 flex-row items-center gap-4">
                        <div className="w-full">
                            <p className="mb-2 font-medium">
                                Ano
                            </p>
                            <Input
                                type="text"
                                register={register}
                                name="year"
                                error={errors.year?.message}
                                placeholder="Ex.: 2023/2023"
                            />
                        </div>
                        <div className="w-full">
                            <p className="mb-2 font-medium">
                                KM rodados
                            </p>
                            <Input
                                type="text"
                                register={register}
                                name="km"
                                error={errors.km?.message}
                                placeholder="Ex.: 15.500"
                            />
                        </div>
                    </div>
                    <div className="flex w-full mb-3 flex-row items-center gap-4">
                        <div className="w-full">
                            <p className="mb-2 font-medium">
                                WhatsApp para contato
                            </p>
                            <Input
                                type="text"
                                register={register}
                                name="whatsapp"
                                error={errors.whatsapp?.message}
                                placeholder="Ex.: 47996028433"
                            />
                        </div>
                        <div className="w-full">
                            <p className="mb-2 font-medium">
                                Cidade
                            </p>
                            <Input
                                type="text"
                                register={register}
                                name="city"
                                error={errors.city?.message}
                                placeholder="Ex.: Florianópolis"
                            />
                        </div>
                    </div>
                    <div className="mb-3">
                        <p className="mb-2 font-medium">
                            Preço
                        </p>
                        <Input
                            type="text"
                            register={register}
                            name="price"
                            error={errors.price?.message}
                            placeholder="Ex.: R$ 300.000"
                        />
                    </div>
                    <div className="mb-3">
                        <p className="mb-2 font-medium">
                            Descrição
                        </p>
                        <textarea
                            className="border-2 w-full rounded-md h-24 px-2"
                            {...register("description")}
                            name="description"
                            id="description"
                            placeholder="Digite a descrição do imóvel..."
                        />
                        {errors.description && <p className="mb-1 text-red-500">{errors.description.message}</p>}
                    </div>

                    <button
                        type="submit"
                        className="w-full h-10 rounded-md bg-zinc-900 text-white font-medium"
                    >
                        Cadastrar
                    </button>
                </form>

            </div>
        </Container>
    )
}
