import { useEffect, useRef } from "react";

export default function StarfieldBackground() {
  const canvasRef = useRef<HTMLCanvasElement>(null);

useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext("2d");
    if (!ctx) return;

    // Criar estrelas
    const stars: Array<{
      x: number;
      y: number;
      size: number;
      opacity: number;
      opacityDirection: number;
      speedX: number;
      speedY: number;
    }> = [];

    const initStars = () => {
      stars.length = 0;
      for (let i = 0; i < 120; i++) {
        stars.push({
          x: Math.random() * canvas.width,
          y: Math.random() * canvas.height,
          size: Math.random() * 1.5 + 0.5,
          opacity: Math.random() * 0.7 + 0.2,
          opacityDirection: Math.random() > 0.5 ? 1 : -1,
          speedX: (Math.random() - 0.5) * 0.2,
          speedY: (Math.random() - 0.5) * 0.2,
        });
      }
    };

    // Configurar tamanho do canvas
    const resizeCanvas = () => {
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
      initStars(); // Reinicializar estrelas ao redimensionar
    };

    // Inicializar canvas
    resizeCanvas();

    // Debounce para resize (evitar lag)
    let resizeTimeout: number;
    const handleResize = () => {
      clearTimeout(resizeTimeout);
      resizeTimeout = window.setTimeout(() => {
        resizeCanvas();
      }, 150);
    };

    // Adicionar event listener para resize
    window.addEventListener("resize", handleResize);

    // Animação
    let animationId: number;
    const animate = () => {
      ctx.fillStyle = "#0a0a0a";
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      stars.forEach((star) => {
        // Atualizar posição
        star.x += star.speedX;
        star.y += star.speedY;

        // Bounce nas bordas
        if (star.x < 0 || star.x > canvas.width) star.speedX *= -1;
        if (star.y < 0 || star.y > canvas.height) star.speedY *= -1;

        // Manter dentro do canvas
        star.x = Math.max(0, Math.min(canvas.width, star.x));
        star.y = Math.max(0, Math.min(canvas.height, star.y));

        // Atualizar opacidade (efeito de brilho)
        star.opacity += star.opacityDirection * 0.005;
        if (star.opacity >= 0.9 || star.opacity <= 0.2) {
          star.opacityDirection *= -1;
        }

        // Desenhar estrela
        ctx.fillStyle = `rgba(255, 255, 255, ${star.opacity})`;
        ctx.beginPath();
        ctx.arc(star.x, star.y, star.size, 0, Math.PI * 2);
        ctx.fill();
      });

      animationId = requestAnimationFrame(animate);
    };

    animate();

    return () => {
      window.removeEventListener("resize", handleResize);
      clearTimeout(resizeTimeout);
      cancelAnimationFrame(animationId);
    };
  }, []);

  return (
    <canvas
      ref={canvasRef}
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100%",
        height: "100%",
        zIndex: 0,
        background: "#0a0a0a",
        pointerEvents: "none",
      }}
    />
  );
}